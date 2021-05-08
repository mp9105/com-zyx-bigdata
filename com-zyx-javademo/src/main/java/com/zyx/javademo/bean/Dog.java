package com.zyx.javademo.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 15:27
 * desc: 测试类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dog {
    @JSONField(name = "ID")
    String id;
    @JSONField(name = "BODY_COLOR")
    String bodyColor;
}
