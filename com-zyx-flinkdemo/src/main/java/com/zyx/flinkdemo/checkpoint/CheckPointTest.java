package com.zyx.flinkdemo.checkpoint;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import com.zyx.flinkdemo.utils.KafkaUtil;

/**
 * @author zyx
 * @since 2021/04/19 22:36
 */
public class CheckPointTest {
    public static void main(String[] args) throws Exception {
        // 创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置并行度
        env.setParallelism(2);
        // 设置检查点
        env.enableCheckpointing(5000);
        CheckpointConfig ckConfig = env.getCheckpointConfig();
        ckConfig.setCheckpointTimeout(60000);
        // 状态后端
        env.setStateBackend(new FsStateBackend("hdfs://192.168.31.201:8020/test/checkpoint_test"));

        DataStreamSource<String> kafkaDS = env.addSource(KafkaUtil.getKafkaSource("kafka_topic_wc", "wc_consumer"));
        DataStream<Tuple2<String, Long>> counts = kafkaDS
                .flatMap(new FlatMapFunction<String, Tuple2<String, Long>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple2<String, Long>> out) throws Exception {
                        String[] words = value.split(" ");
                        for (String word : words) {
                            out.collect(new Tuple2<>(word, 1L));
                        }
                    }
                })
                .keyBy((KeySelector<Tuple2<String, Long>, Object>) value -> value.f0)
                .sum(1);

        counts.print();
        // 执行环境
        env.execute();
    }
}
