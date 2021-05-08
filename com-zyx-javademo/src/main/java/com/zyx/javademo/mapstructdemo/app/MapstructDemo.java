package com.zyx.javademo.mapstructdemo.app;

import com.alibaba.fastjson.JSONObject;
import com.zyx.javademo.mapstructdemo.bean.EmpDAO;
import com.zyx.javademo.mapstructdemo.bean.EmpDTO;
import com.zyx.javademo.mapstructdemo.mapper.EmpMapper;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 17:25
 * desc:
 */
public class MapstructDemo {
    public static void main(String[] args) {
        EmpDAO empDAO = new EmpDAO(10, "zhangsan", 180, 109.233333, 1620355008439L);
        EmpDTO empDTO = EmpMapper.INSTANCE.toEmpDTO(empDAO);
        System.out.println(JSONObject.toJSONString(empDTO));

    }
}
