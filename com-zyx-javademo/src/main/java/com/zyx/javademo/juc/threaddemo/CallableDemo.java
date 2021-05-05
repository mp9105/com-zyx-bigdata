package com.zyx.javademo.juc.threaddemo;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author zyx
 * @since 2021/5/4 17:31
 * desc: Callable接口相关代码
 */
public class CallableDemo {
    public static void main(String[] args) {
        // 查看计算机核数
        System.out.println(Runtime.getRuntime().availableProcessors());
        // 查看系统信息
        Map<String, String> getenv = System.getenv();
        for (String key : getenv.keySet()) {
            System.out.println("key==>" + key + ",value==>" + getenv.get(key));
        }

        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallableThread());
        new Thread(futureTask, "thread1").start();
        new Thread(futureTask, "thread2").start(); // 多个线程执行一个FutureTask, 只会执行一个

        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class MyRunnableThread implements Runnable {
    @Override
    public void run() {

    }
}

/**
 * Callable接口可以带返回值, Runnable接口无返回值
 */
class MyCallableThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " come in callable.");
        TimeUnit.SECONDS.sleep(2);
        return 1024;
    }
}