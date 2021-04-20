package com.zyx.kafkademo.producer;

import com.zyx.kafkademo.common.CommonConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;

/**
 * @author zyx
 * @since 2021/04/20 0:30
 */
public class ProducerDemo {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, CommonConfig.KAFKA_SERVER);
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(prop);


        Random random = new Random();
        String[] words = {"Hello", "World", "Java", "Flume", "Flink", "Kafka"};
        int wordsLen = words.length;

        for (int i = 0; i < 100; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j <= 2 + random.nextInt(3); j++) {
                sb.append(words[random.nextInt(wordsLen)]).append(" ");
            }
            String line = sb.subSequence(0, sb.length() - 1).toString();
            ProducerRecord<String, String> record = new ProducerRecord<>("kafka_topic_wc", line);
            kafkaProducer.send(record);
            System.out.println("producer >>" + line);
        }

        kafkaProducer.close();
    }
}
