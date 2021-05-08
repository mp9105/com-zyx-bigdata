package com.zyx.javademo.mapstructdemo.mapper;

import org.mapstruct.Named;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 17:42
 * desc:
 */
@Named("DoubleStringMapper")
public class DoubleStringMapper {
    @Named("stringByDouble")
    public static String stringByDouble(double d) {
        return String.format("%.2f", d);
    }

    @Named("doubleByString")
    public static String doubleByString(double d) {
        return String.format("%.2f", d);
    }
}
