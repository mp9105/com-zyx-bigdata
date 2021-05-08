package com.zyx.javademo.mapstruct.mapper;

import org.mapstruct.Named;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 16:19
 * desc: String与timestamp之间的转化
 */
@Named("StringTimestampMapper")
public class StringTimestampMapper {

    private static final String dateFormatStr = "yyyy-MM-dd HH:mm:ss";

    @Named("timestampByString")
    public Timestamp timestampByString(String strParam) {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormatStr);
        java.util.Date date = null;
        try {
            date = sf.parse(strParam);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Timestamp(date.getTime());
    }

    @Named("stringByTimestamp")
    public String stringByTimestamp(Timestamp timestamp) {
        DateFormat df = new SimpleDateFormat(dateFormatStr);
        return df.format(timestamp);
    }
}
