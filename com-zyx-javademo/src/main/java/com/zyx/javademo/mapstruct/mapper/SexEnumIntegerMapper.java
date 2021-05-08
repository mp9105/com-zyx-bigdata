package com.zyx.javademo.mapstruct.mapper;

import com.zyx.javademo.mapstruct.bean.SexEnum;
import org.mapstruct.Named;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 16:18
 * desc: SexEnum与Integer之间的转化
 */
@Named("SexEnumIntegerMapper")
public class SexEnumIntegerMapper {
    @Named("sexEnumByInteger")
    public SexEnum sexEnumByInteger(Integer intParam){
        return SexEnum.of(intParam);
    }

    @Named("integerBySexEnum")
    public Integer integerBySexEnum(SexEnum sexEnum){
        return sexEnum.getCode();
    }
}
