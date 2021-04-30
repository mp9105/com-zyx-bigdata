package com.zyx.javademo.time.oldapi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/30 15:14
 * desc:
 */
public class TimeZoneDemo {
    public static void main(String[] args) {
        // Calendar和Date相比，它提供了时区转换的功能。时区用TimeZone对象表示
        // 时区的唯一标识是以字符串表示的ID，我们获取指定TimeZone对象也是以这个ID为参数获取，GMT+09:00、Asia/Shanghai都是有效的时区ID。
        // 要列出系统支持的所有ID，请使用`TimeZone.getAvailableIDs()`。
        // 当前时区
        TimeZone tzDefault = TimeZone.getDefault();
        // GMT+9:00时区
        TimeZone tzGmt9 = TimeZone.getTimeZone("GMT+09:00");
        // 纽约时区
        TimeZone tzNy = TimeZone.getTimeZone("America/New_York");
        // Asia/Shanghai
        System.out.println(tzDefault.getID());
        // GMT+09:00
        System.out.println(tzGmt9.getID());
        // America/New_York
        System.out.println(tzNy.getID());

        // 有了时区，我们就可以对指定时间进行转换
        // 当前时间:
        Calendar c = Calendar.getInstance();
        // 清除所有:
        c.clear();
        // 设置为北京时区:
        c.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        // 设置年月日时分秒:
        c.set(2019, Calendar.DECEMBER, 20, 8, 15, 0);
        // 显示时间:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        // 2019-11-19 19:15:00
        System.out.println(sdf.format(c.getTime()));

    }
}
