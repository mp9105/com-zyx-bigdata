package com.zyx.flinkdemo.prepare;

import net.minidev.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/26 14:59
 * desc: 创建MarketInfo所需文件
 */
public class MarketInfoPrepare {
    public static void sourcePrepare() {
        Set<String> infoSet = new TreeSet<>();
        Random random = new Random();
        for (int outerIndex = 0; outerIndex < 30; outerIndex++) {
            for (int innerIndex = 0; innerIndex < 5; innerIndex++) {
                infoSet.add((1000 + outerIndex) + "_" + random.nextInt(5));
            }
        }

        ArrayList<String> infoList = new ArrayList<>(infoSet);
        FileWriter fw = null;
        BufferedWriter bfw = null;

        int maxIndex = 50000;
        try {
            fw = new FileWriter("input/marketInfo.txt");
            bfw = new BufferedWriter(fw);
            for (int i = 1; i <= maxIndex; i++) {
                for (int j = 0; j < 3; j++) {
                    String info = infoList.get(random.nextInt(infoList.size()));
                    String[] split = info.split("_");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", split[0]);
                    jsonObject.put("group", split[1]);
                    jsonObject.put("price", random.nextInt(20));
                    jsonObject.put("sendTime", System.currentTimeMillis());
                    bfw.write(jsonObject.toJSONString());
                    bfw.newLine();
                }
                if (i % 10 == 0) {
                    bfw.flush();
                }
                Thread.sleep(2);
                bfw.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (bfw != null) {
                try {
                    bfw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
