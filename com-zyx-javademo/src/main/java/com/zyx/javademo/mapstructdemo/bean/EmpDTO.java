package com.zyx.javademo.mapstructdemo.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 16:38
 * desc:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpDTO {
    @JSONField(name = "ID")
    private String id;
    @JSONField(name = "NAME")
    private String name;
    @JSONField(name = "HEIGHT")
    private String height;
    @JSONField(name = "PRICE")
    private String salary;
    @JSONField(name = "DATETIME")
    private String dt;
}
