package com.zyx.javademo.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyx.javademo.bean.Dog;

/**
 * @author zyx
 * @since 2021/04/26 22:45
 */
public class JsonDemo {
    public static void main(String[] args) {
        jsonFieldTest();
    }

    private static void jsonFieldTest() {
        Dog dog = new Dog("gousheng", "yellow");
        String dogJson = JSONObject.toJSONString(dog);
        System.out.println(dogJson);

        System.out.println(dog.getBodyColor());
    }

    private static void jsonArrayTest() {
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
