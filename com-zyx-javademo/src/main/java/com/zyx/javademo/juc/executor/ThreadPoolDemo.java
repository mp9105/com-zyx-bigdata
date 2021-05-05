package com.zyx.javademo.juc.executor;

/**
 * @author zyx
 * @since 2021/5/5 16:42
 * desc:线程池实例
 * Java中的线程池是通过Executor框架实现的，该框架中用到了Executor，Executors，ExecutorService，ThreadPoolExecutor这几个类。
 * 了解
 *  Executors.newScheduledThreadPool() - 带有调度功能
 *  Executors.newWorkStealingPool(int) - Java8新增，使用目前机器上可用的处理器作为它的并行级别
 * 重点

 *  Executors.newFixedThreadPool(int)
 *      一池固定数线程！
 *      创建一个 定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 *      newFixedThreadPool创建的线程池corePoolSize和maximumPoolSize值是相等的，它使用的LinkedBlockingQueue
 *  Executors.newSingleThreadExecutor()
 *      一池一线程@
 *      创建一个 单线程化 的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序执行。
 *      newSingleThreadExecutor将corePoolSize和maximumPoolSize都设置为1，它使用的LinkedBlockingQueue。
 *  Executors.newCachedThreadPool()
 *      一池多线程！
 *      创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 *      newCachedThreadPool将corePoolSize设置为0，将maximumPoolSize设置为Integer.MAX_VALUE，
 *      使用的SynchronousQueue，也就是说来了任务就创建线程运行，当线程空闲超过60秒，就销毁线程。
 */
public class ThreadPoolDemo {
}
