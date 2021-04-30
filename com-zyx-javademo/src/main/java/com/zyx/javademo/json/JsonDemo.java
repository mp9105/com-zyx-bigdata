package com.zyx.javademo.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zyx
 * @since 2021/04/26 22:45
 */
public class JsonDemo {
    public static void main(String[] args) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name", "zhangsan");
        jsonObject1.put("sex", "man");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name", "zhangsan");
        jsonObject2.put("sex", "woman");
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        System.out.println(jsonArray.toJSONString());
    }
}
