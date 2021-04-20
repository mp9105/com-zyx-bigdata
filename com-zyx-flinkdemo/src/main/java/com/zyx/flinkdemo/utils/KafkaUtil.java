package com.zyx.flinkdemo.utils;

import com.zyx.flinkdemo.common.CommonConfig;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

/**
 * @author zyx
 * @since 2021/04/19 22:38
 */
public class KafkaUtil {
    public static FlinkKafkaConsumer<String> getKafkaSource(String topic, String groupId) {
        Properties prop = new Properties();
        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, CommonConfig.KAFKA_SERVER);
        prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new FlinkKafkaConsumer<String>(topic, new SimpleStringSchema(), prop);
    }
}
