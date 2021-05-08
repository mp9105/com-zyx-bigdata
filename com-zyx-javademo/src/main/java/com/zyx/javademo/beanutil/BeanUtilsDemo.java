package com.zyx.javademo.beanutil;

import com.zyx.javademo.bean.Emp;
import com.zyx.javademo.bean.User;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/7 17:11
 * desc: BeanUtils工具测试
 */
public class BeanUtilsDemo {
    public static void main(String[] args) {
        copyDemo1();
    }

    public static void copyDemo1() {
        List<User> list = new ArrayList<>();
        User user1 = new User("zhangsan", 18, 180);
        User user2 = new User("lisi", 18, 180);
        User user3 = new User("wangwu", 18, 180);
        list.add(user1);
        list.add(user2);
        list.add(user3);

        List<Emp> emps = copyValues(list, Emp.class);

        for (Emp emp : emps) {
            System.out.println(emp);
        }
    }

    public static void copyDemo2() {
        List<Emp> list = new ArrayList<>();

        Emp emp1 = new Emp("zhangsan", "18");
        Emp emp2 = new Emp("lisi", "18");
        Emp emp3 = new Emp("wangwu", "18");
        list.add(emp1);
        list.add(emp2);
        list.add(emp3);

        List<User> users = copyValues(list, User.class);

        for (User user : users) {
            System.out.println(user);
        }

    }


    /**
     * 将列表中对象的值copy到另一种类型的元素的对象中
     * @param originList 原类型数据的列表
     * @param vClass 新元素的类型
     * @param <K> 原有数据类型的泛型
     * @param <V> 新数据类型的泛型
     * @return 新类型数据的列表
     */
    public static <K, V> List<V> copyValues(List<K> originList, Class<V> vClass) {
        List<V> resList = new ArrayList<>();
        for (K key : originList) {
            V value = null;
            try {
                value = vClass.newInstance();
                System.out.println(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (value != null) {
                    BeanUtils.copyProperties(key, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            resList.add(value);
        }
        return resList;
    }
}
