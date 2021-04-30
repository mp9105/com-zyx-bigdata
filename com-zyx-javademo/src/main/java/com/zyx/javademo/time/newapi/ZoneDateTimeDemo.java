package com.zyx.javademo.time.newapi;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/30 14:16
 * ZonedDateTime是带时区的日期和时间，可用于时区转换；
 * ZonedDateTime和LocalDateTime可以相互转换。
 */
public class ZoneDateTimeDemo {
    public static void main(String[] args) {
        // LocalDateTime总是表示本地日期和时间，要表示一个带时区的日期和时间，我们就需要ZonedDateTime。
        // 可以简单地把ZonedDateTime理解成LocalDateTime加ZoneId。ZoneId是java.time引入的新的时区类，注意和旧的java.util.TimeZone区别。
        // 要创建一个ZonedDateTime对象，有以下几种方法
        // ① 通过now()方法返回当前时间：
        // 默认时区
        ZonedDateTime zbj = ZonedDateTime.now();
        // 2019-09-15T20:58:18.786182+08:00[Asia/Shanghai]
        System.out.println(zbj);
        // 用指定时区获取当前时间
        ZonedDateTime zny = ZonedDateTime.now(ZoneId.of("America/New_York"));
        // 2019-09-15T08:58:18.788860-04:00[America/New_York]
        System.out.println(zny);
        // ② 通过给一个LocalDateTime附加一个ZoneId，就可以变成ZonedDateTime
        LocalDateTime ldt = LocalDateTime.of(2019, 9, 15, 15, 16, 17);
        ZonedDateTime zoneBj = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime zoneNy = ldt.atZone(ZoneId.of("America/New_York"));
        System.out.println(zoneBj);
        System.out.println(zoneNy);


        // 时区转换
        // 要转换时区，首先我们需要有一个ZonedDateTime对象，然后，通过withZoneSameInstant()将关联时区转换到另一个时区，转换后日期和时间都会相应调整。
        // 以中国时区获取当前时间:
        ZonedDateTime zoneBj2 = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        // 转换为纽约时间:
        ZonedDateTime zoneNy2 = zoneBj2.withZoneSameInstant(ZoneId.of("America/New_York"));
        System.out.println(zoneBj2);
        System.out.println(zoneNy2);

        // 要特别注意，时区转换的时候，由于夏令时的存在，不同的日期转换的结果很可能是不同的。
        // 这是北京时间9月15日的转换结果：
        //      2019-09-15T21:05:50.187697+08:00[Asia/Shanghai]
        //      2019-09-15T09:05:50.187697-04:00[America/New_York]
        // 这是北京时间11月15日的转换结果：
        //      2019-11-15T21:05:50.187697+08:00[Asia/Shanghai]
        //      2019-11-15T08:05:50.187697-05:00[America/New_York]

        // 有了ZonedDateTime，将其转换为本地时间就非常简单
        // 转换为LocalDateTime时，直接丢弃了时区信息。
        ZonedDateTime zoneDt = ZonedDateTime.now();
        LocalDateTime localDt = zoneDt.toLocalDateTime();
    }
}
