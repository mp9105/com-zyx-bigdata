package com.zyx.flinkdemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/26 16:01
 * desc: 含有窗口关闭时间的用户信息Pojo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoWithWindowEnd {
    private String userId;
    private String groupId;
    private int targetCount;
    private long sendTime;
    private long windowEnd;
}
