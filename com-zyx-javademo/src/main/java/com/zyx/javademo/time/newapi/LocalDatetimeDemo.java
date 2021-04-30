package com.zyx.javademo.time.newapi;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/30 13:51
 * Java 8引入了新的日期和时间API
 *      它们是不变类，默认按ISO 8601标准格式化和解析；
 *      使用LocalDateTime可以非常方便地对日期和时间进行加减，或者调整日期和时间，它总是返回新对象；
 *      使用isBefore()和isAfter()可以判断日期和时间的先后；
 *      使用Duration和Period可以表示两个日期和时间的“区间间隔”。
 */
public class LocalDatetimeDemo {
    public static void main(String[] args) {
        // 相互转换
        // 当前日期和时间
        LocalDateTime dt = LocalDateTime.now();
        // 转换到当前日期
        LocalDate d = dt.toLocalDate();
        // 转换到当前时间
        LocalTime t = dt.toLocalTime();
        // 指定日期和时间
        // 2019-11-30, 注意11=11月
        LocalDate d2 = LocalDate.of(2019, 11, 30);
        // 15:16:17
        LocalTime t2 = LocalTime.of(15, 16, 17);
        LocalDateTime dt2 = LocalDateTime.of(2019, 11, 30, 15, 16, 17);
        LocalDateTime dt3 = LocalDateTime.of(d2, t2);
        System.out.println(dt3);


        // 将字符串转换为LocalDateTime, 严格按照ISO 8601的格式传入
        LocalDateTime localDateTime = LocalDateTime.parse("2019-11-19T15:16:17");
        LocalDate localDate = LocalDate.parse("2019-11-19");
        LocalTime localTime = LocalTime.parse("15:16:17");


        // ISO 8601规定的日期和时间分隔符是T。标准格式如下：
        // 日期：yyyy-MM-dd
        // 时间：HH:mm:ss
        // 带毫秒的时间：HH:mm:ss.SSS
        // 日期和时间：yyyy-MM-dd'T'HH:mm:ss
        // 带毫秒的日期和时间：yyyy-MM-dd'T'HH:mm:ss.SSS
        // 自定义格式化
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDatetime = dtf.format(LocalDateTime.now());
        // 用自定义格式进行解析
        LocalDateTime parseDatetime = LocalDateTime.parse("2021-04-13 13:22:28", dtf);


        // 对日期和时间进行加减的非常简单的链式调用
        // 加五天减三分钟
        LocalDateTime operDatetime = localDateTime.plusDays(5).minusMinutes(3);
        System.out.println(dtf.format(operDatetime));


        // 注意到月份加减会自动调整日期，例如从2019-10-31减去1个月得到的结果是2019-09-30，因为9月没有31日。
        // 对日期和时间进行调整则使用withXxx()方法，例如：withHour(15)会把10:11:12变为15:11:12
        LocalDateTime dateTime = LocalDateTime.of(2019, 10, 26, 20, 30, 59);
        System.out.println(dateTime);
        // 日期变为31日:
        LocalDateTime dateTime2 = dt.withDayOfMonth(31);
        // 2019-10-31T20:30:59
        System.out.println(dateTime2);
        // 月份变为9:
        LocalDateTime dateTime3 = dt2.withMonth(9);
        // 2019-09-30T20:30:59
        System.out.println(dateTime3);


        // 同样注意到调整月份时，会相应地调整日期，即把2019-10-31的月份调整为9时，日期也自动变为30。
        // 实际上，LocalDateTime还有一个通用的with()方法允许我们做更复杂的运算。
        // 本月第一天0:00时刻
        LocalDateTime firstDay = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        System.out.println(firstDay);
        // 本月最后1天
        LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(lastDay);
        // 下月第1天
        LocalDate nextMonthFirstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println(nextMonthFirstDay);
        // 本月第1个周一
        LocalDate firstWeekday = LocalDate.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println(firstWeekday);


        // 要判断两个LocalDateTime的先后，可以使用isBefore()、isAfter()方法，对于LocalDate和LocalTime类似：
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = LocalDateTime.of(2019, 11, 19, 8, 15, 0);
        System.out.println(now.isBefore(target));
        System.out.println(LocalDate.now().isBefore(LocalDate.of(2019, 11, 19)));
        System.out.println(LocalTime.now().isAfter(LocalTime.parse("08:15:00")));


        // Duration表示两个时刻之间的时间间隔。另一个类似的Period表示两个日期之间的天数：
        //      注意到两个LocalDateTime之间的差值使用Duration表示，类似PT1235H10M30S，表示1235小时10分钟30秒。
        //      而两个LocalDate之间的差值用Period表示，类似P1M21D，表示1个月21天。
        //      Duration和Period的表示方法也符合ISO 8601的格式，它以P...T...的形式表示，P...T之间表示日期间隔，T后面表示时间间隔。
        //      如果是PT...的格式表示仅有时间间隔。
        LocalDateTime start = LocalDateTime.of(2019, 11, 19, 8, 15, 0);
        LocalDateTime end = LocalDateTime.of(2020, 1, 9, 19, 25, 30);
        Duration dur = Duration.between(start, end);
        // PT1235H10M30S
        System.out.println(dur);
        Period per = LocalDate.of(2019, 11, 19).until(LocalDate.of(2020, 1, 9));
        // P1M21D
        System.out.println(per);
        // 利用ofXxx()或者parse()方法也可以直接创建Duration
        // 10 hours
        Duration dur1 = Duration.ofHours(10);
        // 1 day, 2 hours, 3 minutes
        Duration dur2 = Duration.parse("P1DT2H3M");




    }
}
