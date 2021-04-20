package com.zyx.flinkdemo.source;

import com.zyx.flinkdemo.utils.KafkaUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author zyx
 * @since 2021/04/20 23:18
 */
public class FlinkKafkaConsumerDemo {
    public static void main(String[] args) throws Exception {
        // 创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // KafkaFLinkConsumer
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
