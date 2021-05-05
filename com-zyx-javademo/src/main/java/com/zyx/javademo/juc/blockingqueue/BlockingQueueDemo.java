package com.zyx.javademo.juc.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zyx
 * @since 2021/5/5 15:03
 * desc: 阻塞队列案例
 * 1.阻塞队列
 *  ① 阻塞队列有没有好的一面
 *  ② 不得不阻塞, 你如何管理
 * 2.阻塞队列是什么?
 *  ① 当阻塞队列是空时，从队列中获取元素的操作将会被阻塞。
 *  ② 当阻塞队列是满时，往队列里添加元素的操作将会被阻塞。
 *  ③ 试图从空的阻塞队列中获取元素的线程将会被阻塞，直到其他的线程往空的队列插入新的元素。
 *  ④ 试图往已满的阻塞队列中添加新元素的线程同样也会被阻塞，直到其他的线程从列中移除一个或者多个元素或者完全清空队列
 * 3.有什么好处？
 *  在多线程领域：所谓阻塞，在某些情况下余挂起线程（即阻塞），一旦条件满足，被挂起的线程又会自动被唤醒
 * 4.为什么需要BlockingQueue
 *  ① 好处是我们不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，因为这一切BlockingQueue都给你一手包办了
 *  ② 在Concurrent包发布以前，在多线程环境下，我们每个程序员都必须去自己控制这些细节，尤其还要兼顾效率和线程安全，而这会给我们的程序带来不小的复杂度。
 * 5.BlockingQueue常用种类
 *  ① ArrayBlockingQueue：由数组结构组成的有界阻塞队列。
 *  ② LinkedBlockingQueue：由链表结构组成的有界（但大小默认值为Integer.MAX_VALUE，因此可以认为是无界的）阻塞队列
 *  ③ PriorityBlockingQueue：支持优先级排序的无界阻塞队列。
 *  ④ DelayQueue：使用优先级队列实现妁延迟无界阻塞队列。
 *  ⑤ SynchronousQueue：不存储元素的阻塞队列，也即是单个元素的队列（可以理解为生产一个消费一个）
 *  ⑥ LinkedTransferQueue：由链表结构绒成的无界阻塞队列。
 *  ⑦ LinkedBlockingDeque：由链表结构组成的双向阻塞队列。(注意是Deque)
 *  重点：ArrayBlockingQueue、LinkedBlockingQueue、不存储元素的阻塞队列
 * 6.BlockingQueue的核心方法
 *  方法类型	抛出异常	    特殊值	    阻塞	    超时
 *  插入	    add(e)	    offer(e)	put(e)	offer(e,time,unit)
 *  移除	    remove()	poll()	    take()	poll(time,unit)
 *  检查	    element()	peek()	    不可用	不可用
 *  -------------------------------------------------------------------------------
 *  性质	    说明
 *  抛出异常	当阻塞队列满时：在往队列中add插入元素会抛出 IIIegalStateException：Queue full
 *          当阻塞队列空时：再往队列中remove移除元素，会抛出NoSuchException
 *  特殊性	插入方法，成功true，失败false
 *          移除方法：成功返回出队列元素，队列没有就返回空
 *  一直阻塞	当阻塞队列满时，生产者继续往队列里put元素，队列会一直阻塞生产线程直到put数据or响应中断退出。
 *          当阻塞队列空时，消费者线程试图从队列里take元素，队列会一直阻塞消费者线程直到队列可用。
 *  超时退出	当阻塞队列满时，队里会阻塞生产者线程一定时间，超过限时后生产者线程会退出
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        // BlockingQueueDemo.throwsBlockingApi();
        // BlockingQueueDemo.specialValueBlockingApi();
        // BlockingQueueDemo.blockedBlockingApi();
        BlockingQueueDemo.timeOutBlockingApi();
    }

    /**
     * 抛出异常的API
     */
    public static void throwsBlockingApi() {
        // List<String> list = new ArrayList<>();
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        // 队列满时使用add添加时将会抛出异常
        // System.out.println(blockingQueue.add("d"));

        // 查看队首元素
        System.out.println(blockingQueue.peek());

        // 先进先出
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        // 没有元素时再次remove将会抛异常
        // System.out.println(blockingQueue.remove());
    }

    /**
     * 特殊值API
     */
    public static void specialValueBlockingApi() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        // 队列满时使用offer添加时将会返回false
        System.out.println(blockingQueue.offer("d"));


        System.out.println(blockingQueue.peek());

        // 先进先出
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        // 没有元素时再次poll将会返回null
        System.out.println(blockingQueue.poll());
    }

    /**
     * 阻塞API
     * @throws InterruptedException 线程阻塞异常
     */
    public static void blockedBlockingApi() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        // 队列满时使用put添加将会阻塞
        System.out.println("============================");
        // blockingQueue.put("d");

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        // 队列空时使用take取出将会阻塞
        System.out.println("*****************************");
        blockingQueue.take();
    }

    /*(*

     */
    public static void timeOutBlockingApi() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c", 2L, TimeUnit.SECONDS));
        // 队列满后 阻塞2s后 退出
        System.out.println(blockingQueue.offer("d", 2L, TimeUnit.SECONDS));

    }
}
