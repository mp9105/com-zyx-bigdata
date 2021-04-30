package com.zyx.flinkdemo.utils;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/28 10:53
 * desc:
 */
public class TestLog {
    @Test
    public void testLog() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        System.out.println("hello");

        env.execute();
    }
}
