package com.zyx.javademo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/7 17:12
 * desc: 雇员类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emp {
    String name;
    String age;
}
