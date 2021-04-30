package com.zyx.flinkdemo.datastream;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyx.flinkdemo.pojo.MarketInfo;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.TreeSet;


/**
 * @author Yaxi.Zhang
 * @since 2021/4/26 14:19
 * desc: 对交易信息在一个窗口内按照group进行分组, 在窗口内进行去重并按照最新的价格进行排序
 */
public class GroupAndDistinct {
    public static void main(String[] args) throws Exception {
        // MarketInfoPrepare.sourcePrepare();
        // System.out.println("================== 文件准备完毕 ==================");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 获取数据源并提取事件时间
        env.setParallelism(1);
        env
                .readTextFile("input/marketInfo.txt")
                .map(new MapFunction<String, MarketInfo>() {
                    @Override
                    public MarketInfo map(String value) throws Exception {
                        return JSONObject.parseObject(value, MarketInfo.class);
                    }
                })
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy.<MarketInfo>forMonotonousTimestamps()
                                .withTimestampAssigner(new SerializableTimestampAssigner<MarketInfo>() {
                                    @Override
                                    public long extractTimestamp(MarketInfo element, long recordTimestamp) {
                                        return element.getSendTime();
                                    }
                                })
                )
                // 按照groupId分组
                .keyBy(MarketInfo::getGroup)
                // 开1秒钟的滚动窗口
                .window(TumblingEventTimeWindows.of(Time.seconds(1)))
                // 获取关窗时间并与原用户信息进行拼接
                .process(new ProcessWindowFunction<MarketInfo, Tuple2<Long, MarketInfo>, String, TimeWindow>() {
                    @Override
                    public void process(String key,
                                        Context ctx,
                                        Iterable<MarketInfo> marketInfos,
                                        Collector<Tuple2<Long, MarketInfo>> out) throws Exception {
                        for (MarketInfo marketInfo : marketInfos) {
                            out.collect(Tuple2.of(ctx.window().getEnd(), marketInfo));
                        }
                    }
                })
                // 按照关窗时间进行分组
                .keyBy(new KeySelector<Tuple2<Long, MarketInfo>, Long>() {
                    @Override
                    public Long getKey(Tuple2<Long, MarketInfo> value) throws Exception {
                        return value.f0;
                    }
                })
                // 对结果进行排序
                .process(new KeyedProcessFunction<Long, Tuple2<Long, MarketInfo>, String>() {
                    private ValueState<Long> timerState;
                    private MapState<Tuple2<String, String>, MarketInfo> marketInfoState;
                    private MapState<String, TreeSet<MarketInfo>> resultState;
                    @Override
                    public void open(Configuration parameters) throws Exception {
                        // 初始化状态量
                        timerState = getRuntimeContext()
                                .getState(new ValueStateDescriptor<>("timer_state", Long.class));
                        marketInfoState = getRuntimeContext()
                                .getMapState(new MapStateDescriptor<>(
                                        "market_info_state",
                                        TypeInformation.of(new TypeHint<Tuple2<String, String>>() {
                                        }),
                                        TypeInformation.of(new TypeHint<MarketInfo>() {
                                        })));
                        resultState = getRuntimeContext()
                                .getMapState(new MapStateDescriptor<>(
                                        "result_state",
                                        TypeInformation.of(new TypeHint<String>() {
                                        }),
                                        TypeInformation.of(new TypeHint<TreeSet<MarketInfo>>() {
                                        })));
                    }
                    @Override
                    public void processElement(Tuple2<Long, MarketInfo> value,
                                               Context ctx,
                                               Collector<String> out) throws Exception {
                        marketInfoState.put(Tuple2.of(value.f1.getId(), value.f1.getGroup()), value.f1);
                        if (timerState.value() == null) {
                            // 窗口关闭10ms后触发操作
                            long timer = value.f0 + 10L;
                            ctx.timerService().registerEventTimeTimer(timer);
                            timerState.update(timer);
                        }
                    }
                    @Override
                    public void onTimer(long timestamp,
                                        OnTimerContext ctx,
                                        Collector<String> out) throws Exception {
                        // 触发操作时进行排序
                        // 获取group
                        for (MarketInfo userInfo : marketInfoState.values()) {
                            TreeSet<MarketInfo> treeSet = resultState.get(userInfo.getGroup());
                            if (treeSet == null) {
                                treeSet = new TreeSet<>((o1, o2) -> {
                                    int result = Integer.compare(o2.getPrice(), o1.getPrice());
                                    // 不让返回0, 是为了必须treeSet去重
                                    return result == 0 ? 1 : result;
                                });
                            }
                            treeSet.add(userInfo);
                            if (treeSet.size() > 3) {
                                treeSet.pollLast();
                            }
                            resultState.put(userInfo.getGroup(), treeSet);
                        }
                        // 拼接结果
                        JSONArray jsonArray = new JSONArray();
                        for (String key : resultState.keys()) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("induCode", key);
                            jsonObject.put("timestamp", timestamp - 10L);
                            jsonObject.put("tops", resultState.get(key));
                            jsonArray.add(jsonObject);
                        }
                        JSONObject resultJsonObject = new JSONObject();
                        resultJsonObject.put("type", "L2-TOP-10");
                        resultJsonObject.put("data", jsonArray);
                        //resultJsonObject.put("ts", timestamp - 10L);
                        // 收集结果
                        out.collect(resultJsonObject.toJSONString());
                        // 清空状态
                        timerState.clear();
                        marketInfoState.clear();
                        resultState.clear();
                    }
                })
                .print();
        env.execute();
    }
}