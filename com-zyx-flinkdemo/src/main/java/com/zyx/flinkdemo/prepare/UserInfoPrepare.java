package com.zyx.flinkdemo.prepare;

import net.minidev.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/26 14:59
 * desc:
 */
public class UserInfoPrepare {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> infos = new ArrayList<>();
        infos.add("1001_1");
        infos.add("1001_2");
        infos.add("1002_2");
        infos.add("1002_4");
        infos.add("1003_1");
        infos.add("1003_3");
        infos.add("1004_5");
        infos.add("1005_3");

        Random random = new Random();

        FileWriter fw = new FileWriter("input/wc.txt");
        BufferedWriter bfw = new BufferedWriter(fw);

        for (int i = 0; i < 10000; i++) {
            String info = infos.get(random.nextInt(infos.size()));
            String[] split = info.split("_");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", split[0]);
            jsonObject.put("groupId", split[1]);
            jsonObject.put("targetCount", random.nextInt(20));
            jsonObject.put("sendTime", System.currentTimeMillis());
            bfw.write(jsonObject.toJSONString());
            bfw.newLine();
            bfw.flush();
            Thread.sleep(10);
        }

        bfw.close();
        fw.close();
    }
}
