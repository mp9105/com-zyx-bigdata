package com.zyx.javademo.juc.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/6 11:28
 * desc: 线程池工具类
 */
public class ExecutorsDemo {
    /**
     * 使用guava的ThreadFactoryBuilder来创建线程池
     */
    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();

    private static ExecutorService pool = new ThreadPoolExecutor(3, 30,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), NAMED_THREAD_FACTORY, new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        int count = 30;
        for (int i = 0; i < count; i++) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pool.execute(new SubThread());
        }
        System.out.println(Thread.currentThread().getName() + " >>> " + "running...");
    }

    private static class SubThread implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getState()+"----------"+Thread.currentThread().getName());
        }
    }
}
