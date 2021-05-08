package com.zyx.javademo.mapstructdemo.mapper;

import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 16:43
 * desc: 时间戳与标准时间字符串的转换
 */
@Named("TsDtfMapper")
public class TsDtfMapper {
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    @Named("dtfByTs")
    public static String dtfByTs(long ts) {
        Instant ins = Instant.ofEpochMilli(ts);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        ZonedDateTime zdf = ZonedDateTime.ofInstant(ins, ZoneId.systemDefault());
        return formatter.format(zdf);
    }

    @Named("tsByDtf")
    public static long tsByDtf(String dtf) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        ZonedDateTime zdt = ZonedDateTime.parse(dtf, formatter);
        return Instant.from(zdt).toEpochMilli();
    }

}