package com.zyx.flinkdemo.datastream;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyx.flinkdemo.pojo.UserInfo;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
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
 * desc: 对用户信息按照group进行分组并在窗口内去重排序
 */
public class GroupAndDistinct {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 获取数据源并提取事件时间
        SingleOutputStreamOperator<UserInfo> userInfoTsInfo = env
                .readTextFile("input/userInfo.txt")
                .map(new MapFunction<String, UserInfo>() {
                    @Override
                    public UserInfo map(String value) throws Exception {
                        return JSONObject.parseObject(value, UserInfo.class);
                    }
                })
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy.<UserInfo>forMonotonousTimestamps()
                                .withTimestampAssigner(new SerializableTimestampAssigner<UserInfo>() {
                                    @Override
                                    public long extractTimestamp(UserInfo element, long recordTimestamp) {
                                        return element.getSendTime();
                                    }
                                })
                );
        userInfoTsInfo
                // 按照groupId分组
                .keyBy(UserInfo::getGroupId)
                // 开1秒钟的滚动窗口
                .window(TumblingEventTimeWindows.of(Time.seconds(1)))
                // 获取关窗时间并与原用户信息进行拼接
                .process(new ProcessWindowFunction<UserInfo, Tuple2<Long, UserInfo>, String, TimeWindow>() {
                    @Override
                    public void process(String key,
                                        Context ctx,
                                        Iterable<UserInfo> userInfos,
                                        Collector<Tuple2<Long, UserInfo>> out) throws Exception {
                        for (UserInfo userInfo : userInfos) {
                            out.collect(Tuple2.of(ctx.window().getEnd(), userInfo));
                        }
                    }
                })
                // 按照关窗时间进行分组
                .keyBy(new KeySelector<Tuple2<Long, UserInfo>, Tuple2<Long, String>>() {
                    @Override
                    public Tuple2<Long, String> getKey(Tuple2<Long, UserInfo> value) throws Exception {
                        return Tuple2.of(value.f0, value.f1.getGroupId());
                    }
                })
                // 对结果进行排序
                .process(new KeyedProcessFunction<Tuple2<Long, String>, Tuple2<Long, UserInfo>, String>() {
                    private ValueState<Long> timerState;
                    private MapState<String, UserInfo> userInfoState;
                    private MapState<String, TreeSet> resState;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        // 初始化状态量
                        timerState = getRuntimeContext()
                                .getState(new ValueStateDescriptor<Long>("timer_state", Long.class));
                        userInfoState = getRuntimeContext()
                                .getMapState(new MapStateDescriptor<String, UserInfo>("user_info_state", String.class, UserInfo.class));
                        resState = getRuntimeContext()
                                .getMapState(new MapStateDescriptor<>("result_state", String.class, TreeSet.class));
                    }

                    @Override
                    public void processElement(Tuple2<Long, UserInfo> value,
                                               Context ctx,
                                               Collector<String> out) throws Exception {
                        userInfoState.put(value.f1.getUserId(), value.f1);
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
                        for (UserInfo userInfo : userInfoState.values()) {
                            TreeSet treeSet = resState.get(userInfo.getGroupId());
                            if (treeSet == null) {
                                treeSet = new TreeSet<>((o1, o2) -> {
                                    UserInfo ui1 = (UserInfo) o1;
                                    UserInfo ui2 = (UserInfo) o2;
                                    int result = Integer.compare(ui2.getTargetCount(), ui1.getTargetCount());
                                    return result == 0 ? 1 : result;  // 不让返回0, 是为了必须treeSet去重
                                });
                            }
                            treeSet.add(userInfo);
                            if (treeSet.size() > 3) {
                                treeSet.pollLast();
                            }
                            resState.put(userInfo.getGroupId(), treeSet);
                        }

                        JSONObject jsonObject = new JSONObject();
                        JSONArray jsonArray = new JSONArray();
                        for (String key : resState.keys()) {
                            jsonObject.put(key, resState.get(key));
                        }

                        // 清空状态
                        timerState.clear();
                        userInfoState.clear();
                        resState.clear();
                    }
                })
                .print();

        env.execute();
    }
}
