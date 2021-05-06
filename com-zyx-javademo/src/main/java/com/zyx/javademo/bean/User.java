package com.zyx.javademo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/6 14:27
 * desc: 用户类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String name;
    int age;
}
