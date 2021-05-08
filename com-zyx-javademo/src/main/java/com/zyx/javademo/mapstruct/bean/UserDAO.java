package com.zyx.javademo.mapstruct.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 16:10
 * desc: 用户接口类
 */
@Data
public class UserDAO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 姓名
     */
    private Integer sex;
    /**
     * 描述
     */
    private String remark;
    /**
     * 创建时间
     */
    private Timestamp createTime;

    public UserDAO() {
    }
}
