package com.zyx.javademo.juc.threadpool;

import java.util.concurrent.*;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/23 15:25
 * desc: 测试任务队列
 * workQueue任务队列:
 *      直接提交队列：设置为SynchronousQueue队列，SynchronousQueue是一个特殊的BlockingQueue，它没有容量，
 *          每执行一个插入操作就会阻塞，需要再执行一个删除操作才会被唤醒，反之每一个删除操作也都要等待对应的插入操作。
 *      有界的任务队列：有界的任务队列可以使用ArrayBlockingQueue实现
 *          使用ArrayBlockingQueue有界任务队列，若有新的任务需要执行时，线程池会创建新的线程，直到创建的线程数量达到corePoolSize时，则会将新的任务加入到等待队列中。
 *          若等待队列已满，即超过ArrayBlockingQueue初始化的容量，则继续创建线程，直到线程数量达到maximumPoolSize设置的最大线程数量，若大于maximumPoolSize，则执行拒绝策略。
 *          在这种情况下，线程数量的上限与有界任务队列的状态有直接关系，如果有界队列初始容量较大或者没有达到超负荷的状态，线程数将一直维持在corePoolSize以下，
 *          反之当任务队列已满时，则会以maximumPoolSize为最大线程数上限。
 *      无界的任务队列：无界任务队列可以使用LinkedBlockingQueue实现
 *          使用无界任务队列，线程池的任务队列可以无限制的添加新的任务，而线程池创建的最大线程数量就是你corePoolSize设置的数量，
 *          也就是说在这种情况下maximumPoolSize这个参数是无效的，哪怕你的任务队列中缓存了很多未执行的任务，当线程池的线程数达到corePoolSize后，就不会再增加了；
 *          若后续有新的任务加入，则直接进入队列等待，当使用这种任务队列模式时，一定要注意你任务提交与处理之间的协调与控制，
 *          不然会出现队列中的任务由于无法及时处理导致一直增长，直到最后资源耗尽的问题。
 *      优先任务队列：优先任务队列通过PriorityBlockingQueue实现
 *          除了第一个任务直接创建线程执行外，其他的任务都被放入了优先任务队列，按优先级进行了重新排列执行，且线程池的线程数一直为corePoolSize，也就是只有一个。
 */
public class WorkQueueThreadDemo {
    private static ExecutorService pool;
    public static void main(String[] args)  {
        {
            // 直接提交队列
            // maximumPoolSize设置为2 ，拒绝策略为AbortPolic策略，直接抛出异常
            // pool = new ThreadPoolExecutor(1, 2, 1000, TimeUnit.MILLISECONDS,
            //         new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

            // 有界的任务队列
            // pool = new ThreadPoolExecutor(1, 3, 1000, TimeUnit.MILLISECONDS,
            //         new ArrayBlockingQueue<Runnable>(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

            // 无界的任务队列
            pool = new ThreadPoolExecutor(1, 3, 1000, TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

            for (int i = 0; i < 5; i++) {
                pool.execute(new ThreadTask());
            }
        }
    }
}
