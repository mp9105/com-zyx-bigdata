package com.zyx.javademo.collection.set;

import java.util.*;

/**
 * @author zyx
 * @since 2021/04/26 23:18
 */
public class SetTest {
    public static void main(String[] args) {
        Set<String> infos = new TreeSet<>();
        Random random = new Random();

        for (int outerIndex = 1; outerIndex <= 20; outerIndex++) {
            for (int innerIndex = 0; innerIndex < 5; innerIndex++) {
                infos.add((1000 + outerIndex) + "_" + random.nextInt(8));
            }
        }

        System.out.println(infos);
    }
}
