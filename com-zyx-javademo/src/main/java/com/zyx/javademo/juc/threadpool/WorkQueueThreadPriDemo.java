package com.zyx.javademo.juc.threadpool;

import java.util.concurrent.*;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/23 15:56
 * desc: 优先队列任务
 */
public class WorkQueueThreadPriDemo {
    private static ExecutorService pool;
    public static void main(String[] args) {
        // 优先任务队列
        pool = new ThreadPoolExecutor(2, 4, 1000, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 20; i++) {
            pool.execute(new ThreadTaskCom(i));
        }
    }
}