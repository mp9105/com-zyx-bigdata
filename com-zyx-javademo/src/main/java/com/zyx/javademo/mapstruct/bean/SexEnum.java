package com.zyx.javademo.mapstruct.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 16:13
 * desc: 性别枚举类
 */
public enum SexEnum {
    /**
     * 性别信息
     */
    man(1, "男"),
    woman(0, "女");

    @Setter
    @Getter
    private Integer code;

    @Setter
    @Getter
    private String name;

    SexEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SexEnum of(Integer code){
        for(SexEnum sexEnum:SexEnum.values()){
            if(sexEnum.code.equals(code)){
                return sexEnum;
            }
        }
        return null;
    }
}
