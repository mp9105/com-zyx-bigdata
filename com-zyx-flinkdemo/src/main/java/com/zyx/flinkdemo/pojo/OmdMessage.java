package com.zyx.flinkdemo.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 所有行情消息基类
 *
 * @author He.Yong
 * @since 2021.04.07 16:36:08
 */
@Getter
@Setter
public class OmdMessage {
    /**
     * uint32
     * 5 digit security codes with possible
     * values 1 – 99999
     * Uniquely identifies a security
     * available for trading
     */
    private int securityCode;

    /**
     * 消息长度，包括当前字段(uint16)
     */
    private int msgSize;
    /**
     * 消息类型(uint16)
     */
    private int msgType;
    /**
     * 消息序列号
     */
    private long seqNum;
    /**
     * 消息封包的sentTime属性
     */
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.ss")
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.ss")
    private Date sendTime;
}
