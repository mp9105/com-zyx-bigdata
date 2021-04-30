package com.zyx.javademo.time.oldapi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/30 15:10
 * 利用Calendar进行时区转换的步骤是：
 *      清除所有字段；
 *      设定指定时区；
 *      设定日期和时间；
 *      创建SimpleDateFormat并设定目标时区；
 *      格式化获取的Date对象（注意Date对象无时区信息，时区信息存储在SimpleDateFormat中）。
 * 因此，本质上时区转换只能通过SimpleDateFormat在显示的时候完成。
 */
public class CalenderDemo {
    public static void main(String[] args) {
        // Calendar可以用于获取并设置年、月、日、时、分、秒，它和Date比，主要多了一个可以做简单的日期和时间运算的功能。
        // 获取当前时间:
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = 1 + c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        int w = c.get(Calendar.DAY_OF_WEEK);
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.SECOND);
        int ms = c.get(Calendar.MILLISECOND);
        System.out.println(y + "-" + m + "-" + d + " " + w + " " + hh + ":" + mm + ":" + ss + "." + ms);

        // 注意到Calendar获取年月日这些信息变成了get(int field)，返回的年份不必转换，返回的月份仍然要加1，返回的星期要特别注意，1~7分别表示周日，周一，……，周六。
        // Calendar只有一种方式获取，即Calendar.getInstance()，而且一获取到就是当前时间。如果我们想给它设置成特定的一个日期和时间，就必须先清除所有字段：
        // 清除所有
        c.clear();
        // 设置2019年
        c.set(Calendar.YEAR, 2019);
        // 设置9月:注意8表示9月:
        c.set(Calendar.MONTH, 8);
        // 设置2日:
        c.set(Calendar.DATE, 2);
        // 设置时间:
        c.set(Calendar.HOUR_OF_DAY, 21);
        c.set(Calendar.MINUTE, 22);
        c.set(Calendar.SECOND, 23);
        // 2019-09-02 21:22:23
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime()));

        // Calendar也可以对日期和时间进行简单的加减：
        // 清除所有:
        c.clear();
        // 设置年月日时分秒:
        c.set(2019, Calendar.DECEMBER, 20, 8, 15, 0);
        // 加5天并减去2小时:
        c.add(Calendar.DAY_OF_MONTH, 5);
        c.add(Calendar.HOUR_OF_DAY, -2);
        // 显示时间:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = c.getTime();
        // 2019-11-25 6:15:00
        System.out.println(sdf.format(date));

    }
}
