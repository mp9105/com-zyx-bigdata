package com.zyx.flinkdemo.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/26 14:16
 * desc: 用户信息Pojo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketInfo {
    private String id;
    private String group;
    private int price;
    private long sendTime;
}