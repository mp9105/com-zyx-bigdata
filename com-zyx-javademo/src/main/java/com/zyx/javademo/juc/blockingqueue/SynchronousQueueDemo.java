package com.zyx.javademo.juc.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zyx
 * @since 2021/5/5 16:03
 * desc:SynchronousQueue实例
 * SynchronousQueue
 *  不存储元素的阻塞队列，也即是单个元素的队列
 *  可以理解为生产一个消费一个
 *  一个put对应一个take，put元素后将会阻塞，直到take元素
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                // SynchronousQueue中放入元素后只有等到take后才会put下一个元素
                // 该线程中虽然放入元素时没有sleep， 但由于take时sleep了3s， 因此只有take后才能put
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                blockingQueue.put("1");

                System.out.println(Thread.currentThread().getName() + "\t put 2");
                blockingQueue.put("2");

                System.out.println(Thread.currentThread().getName() + "\t put 3");
                blockingQueue.put("3");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "thread1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(blockingQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(blockingQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(blockingQueue.take());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "thread2").start();


    }
}
