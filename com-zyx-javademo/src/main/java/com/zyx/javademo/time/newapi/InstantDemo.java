package com.zyx.javademo.time.newapi;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/30 14:35
 * Instant表示高精度时间戳，它可以和ZonedDateTime以及long互相转换。
 */
public class InstantDemo {
    public static void main(String[] args) {
        // 计算机存储的当前时间，本质上只是一个不断递增的整数。Java提供的System.currentTimeMillis()返回的就是以毫秒表示的当前时间戳。
        // 这个当前时间戳在java.time中以Instant类型表示，我们用Instant.now()获取当前时间戳，效果和System.currentTimeMillis()类似：
        Instant now = Instant.now();
        // 秒, 1568568760
        System.out.println(now.getEpochSecond());
        // 毫秒, 1568568760316
        System.out.println(now.toEpochMilli());

        // 实际上，Instant内部只有两个核心字段：
        // 一个是以秒为单位的时间戳，一个是更精确的纳秒精度。它和System.currentTimeMillis()返回的long相比，只是多了更高精度的纳秒。
        //     private final long seconds;
        //     private final int nanos;


        // 既然Instant就是时间戳，那么，给它附加上一个时区，就可以创建出ZonedDateTime：
        // 以指定时间戳创建Instant:
        Instant ins = Instant.ofEpochSecond(1568568760);
        ZonedDateTime zdt = ins.atZone(ZoneId.systemDefault());
        // 2019-09-16T01:32:40+08:00[Asia/Shanghai]
        System.out.println(zdt);

        // 所以，LocalDateTime，ZoneId，Instant，ZonedDateTime和long都可以互相转换：
        //   LocalDateTime----
        //                   |----ZoneDateTime
        //   ZoneId-----------         |
        //                        -----------
        //                        |         |
        //                      Instant<---->long
        // 转换的时候，只需要留意long类型以毫秒还是秒为单位即可。
    }
}
