package com.zyx.javademo.time.newapi;

import java.time.*;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/30 13:48
 */
public class QuickStart {
    public static void main(String[] args) {
        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        // 获取时区
        // Asia/Shanghai
        ZoneId zoneId = ZoneId.systemDefault();
        // Asia/Shanghai
        ZoneId zoneIdShanghai = ZoneId.of("Asia/Shanghai");
        // +08:00
        ZoneId zoneIdEast8 = ZoneId.of("+8");
        System.out.println(zoneIdEast8);
        // 获取标准时区时间
        Instant now = Instant.now();
        // 获取时间戳
        // 毫秒
        long epochMilli = now.toEpochMilli();
        // 秒
        long epochSecond = now.getEpochSecond();
    }
}
