package com.zyx.javademo.time.oldapi;

import java.util.Date;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/30 15:03
 * java.util.Date是用于表示一个日期和时间的对象，注意与java.sql.Date区分，后者用在数据库中。
 */
public class DateDemo {
    public static void main(String[] args) {
        // Epoch Time是计算从1970年1月1日零点（格林威治时区／GMT+00:00）到现在所经历的秒数，例如：
        // 1574208900表示从从1970年1月1日零点GMT时区到该时刻一共经历了1574208900秒，换算成伦敦、北京和纽约时间分别是：
        // 1574208900 = 北京时间2019-11-20 8:15:00
        //            = 伦敦时间2019-11-20 0:15:00
        //            = 纽约时间2019-11-19 19:15:00
        // 如果观察Date的源码，可以发现它实际上存储了一个long类型的以毫秒表示的时间戳：
        // public class Date implements Serializable, Cloneable, Comparable<Date> {
        //     private transient long fastTime;
        //     ...
        // }
        // 获取当前时间:
        Date date = new Date();
        // 必须加上1900
        System.out.println(date.getYear() + 1900);
        // 0~11，必须加上1
        System.out.println(date.getMonth() + 1);
        // 1~31，不能加1
        System.out.println(date.getDate());
        // 转换为String
        System.out.println(date.toString());
        // 转换为GMT时区
        System.out.println(date.toGMTString());
        // 转换为本地时区
        System.out.println(date.toLocaleString());
    }
}
