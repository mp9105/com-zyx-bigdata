package com.zyx.javademo.juc.threadpool;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/23 15:07
 * desc: 线程任务类
 */
public class ThreadTask implements Runnable {
    public ThreadTask() {

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}