package com.zyx.javademo.time.oldapi;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/30 15:07
 * desc: java.util.Date是用于表示一个日期和时间的对象，注意与java.sql.Date区分，后者用在数据库中。如果观察Date的源码，可以发现它实际上存储了一个long类型的以毫秒表示的时间戳：
 */
public class SimpleDateFormatDemo {
    public static void main(String[] args) {
        // 如果我们想要针对用户的偏好精确地控制日期和时间的格式，就可以使用SimpleDateFormat对一个Date进行转换。它用预定义的字符串表示格式化：
        // yyyy：年
        // MM：月
        // dd: 日
        // HH: 小时
        // mm: 分钟
        // ss: 秒
        // 获取当前时间:
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));

        // Java的格式化预定义了许多不同的格式
        // https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd, yyyy");
        System.out.println(sdf1.format(date));

        // Date对象有几个严重的问题：它不能转换时区，除了toGMTString()可以按GMT+0:00输出外，Date总是以当前计算机系统的默认时区为基础进行输出。
        // 此外，我们也很难对日期和时间进行加减，计算两个日期相差多少天，计算某个月第一个星期一的日期等。
    }
}
