package com.zyx.javademo.juc.threadpool;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/23 16:17
 * desc:
 */
public class ThreadTaskSleep implements Runnable {
    @Override
    public void run() {
        try {
            //让线程阻塞，使后续任务进入缓存队列
            Thread.sleep(1000);
            System.out.println("ThreadName:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
