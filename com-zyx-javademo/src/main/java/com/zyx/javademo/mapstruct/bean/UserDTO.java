package com.zyx.javademo.mapstruct.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 16:21
 * desc: 网络传输对象
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = -2767215193284523251L;
    /**
     * 主键
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private SexEnum sex;
    /**
     * 描述
     */
    private String desc;
    /**
     * 创建时间
     */
    private String createTime;
}