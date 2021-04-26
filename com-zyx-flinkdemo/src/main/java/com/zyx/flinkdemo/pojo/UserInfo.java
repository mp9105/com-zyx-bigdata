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
public class UserInfo {
    private String userId;
    private String groupId;
    private int targetCount;
    private long sendTime;
}