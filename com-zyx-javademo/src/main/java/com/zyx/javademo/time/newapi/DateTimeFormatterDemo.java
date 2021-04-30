package com.zyx.javademo.time.newapi;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/30 14:27
 * 对ZonedDateTime或LocalDateTime进行格式化，需要使用DateTimeFormatter类；
 * DateTimeFormatter可以通过格式化字符串和Locale对日期和时间进行定制输出。
 * 更多格式参考
 *      https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
 */
public class DateTimeFormatterDemo {
    public static void main(String[] args) {
        // 使用旧的Date对象时，我们用SimpleDateFormat进行格式化显示。使用新的LocalDateTime或ZonedLocalDateTime时，我们要进行格式化显示，就要使用DateTimeFormatter。
        // 和SimpleDateFormat不同的是，DateTimeFormatter不但是不变对象，它还是线程安全的。
        // 因为SimpleDateFormat不是线程安全的，使用的时候，只能在方法内部创建新的局部变量。而DateTimeFormatter可以只创建一个实例，到处引用。
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm ZZZZ");
        // 默认方式 ==> 2019-09-15T23:16 GMT+08:00
        System.out.println(formatter.format(zdt));
        DateTimeFormatter zhFormatter = DateTimeFormatter.ofPattern("yyyy MMM dd EE HH:mm", Locale.CHINA);
        // 中国方式 ==> 2019 9月 15 周日 23:16
        System.out.println(zhFormatter.format(zdt));
        DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("E, MMMM/dd/yyyy HH:mm", Locale.US);
        // 美国方式 ==> Sun, September/15/2019 23:16
        System.out.println(usFormatter.format(zdt));


        // 当我们直接调用System.out.println()对一个ZonedDateTime或者LocalDateTime实例进行打印的时候，实际上，调用的是它们的toString()方法
        // 默认的toString()方法显示的字符串就是按照ISO 8601格式显示的，我们可以通过DateTimeFormatter预定义的几个静态变量来引用
        LocalDateTime ldt = LocalDateTime.now();
        // 2019-09-15
        System.out.println(DateTimeFormatter.ISO_DATE.format(ldt));
        // 2019-09-15T23:16:51.56217
        System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(ldt));
    }
}
