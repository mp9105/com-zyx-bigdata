package com.zyx.javademo.collection.collections;

import com.zyx.javademo.bean.User;
import org.junit.Test;

import java.util.*;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/6 14:25
 * desc: Collections工具类
 */
public class CollectionsDemo {
    @Test
    public void testSingleton() {
        User user1 = new User("zhangsan", 20);
        User user2 = new User("lisi2", 20);
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user2);
        System.out.println(list);

        List<Object> objects = Collections.singletonList(null);
        System.out.println(objects);


    }
    
}
