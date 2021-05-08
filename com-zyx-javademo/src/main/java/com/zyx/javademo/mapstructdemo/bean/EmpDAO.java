package com.zyx.javademo.mapstructdemo.bean;

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
public class EmpDAO {
    private int id;
    private String name;
    private int height;
    private double price;
    private long ts;
}
