package com.zyx.flinkdemo.file;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.fs.bucketing.BucketingSink;
import org.apache.flink.streaming.connectors.fs.bucketing.DateTimeBucketer;

import java.time.ZoneId;

/**
 * @author zyx
 * @since 2021/04/15 23:20
 * 向hdfs中按行写入文件并进行滚动
 */
public class Streaming2RowFormatFile {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        // 指定Hadoop用户名称
        System.setProperty("HADOOP_USER_NAME", "hdfs");

        DataStreamSource<String> input = env.socketTextStream("linux201", 6666);
        // Flink1.9已将BucketingSink置为过时的类, 推荐使用StreamingFileSink
        // BucketingSink<String> hadoopSink = new BucketingSink<>("file:///C:\\Users\\43832\\Desktop\\out\\rowformat\\");
        BucketingSink<String> hadoopSink = new BucketingSink<>("hdfs://linux201:8020/data/");
        hadoopSink.setBucketer(new DateTimeBucketer<>("yyyy-MM-dd-HH", ZoneId.of("Asia/Shanghai")));
        // 10秒钟滚动一次
        hadoopSink.setBatchRolloverInterval(10000);
        // 文件达到10MB滚动一次
        hadoopSink.setBatchSize(1024 * 1024 * 10);
        // 文件生成过程 Pending => InProgress => Finished
        // Pending => InProgress滚动, InProgress => Finished checkpoint生成
        hadoopSink.setPendingPrefix("zyx");
        hadoopSink.setPendingSuffix(".zyx");
        hadoopSink.setInProgressPrefix(".");
        input.addSink(hadoopSink);

        env.execute();
    }
}
