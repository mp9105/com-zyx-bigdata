package com.zyx.javademo.juc.threadpool;

import java.util.concurrent.*;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/23 16:14
 * desc:
 * 一般我们创建线程池时，为防止资源被耗尽，任务队列都会选择创建有界任务队列，但种模式下如果出现任务队列已满且线程池创建的线程数达到你设置的最大线程数时，
 * 这时就需要你指定ThreadPoolExecutor的RejectedExecutionHandler参数即合理的拒绝策略，来处理线程池"超载"的情况。
 * ThreadPoolExecutor自带的拒绝策略如下：
 *      AbortPolicy策略：该策略会直接抛出异常，阻止系统正常工作；
 *      CallerRunsPolicy策略：如果线程池的线程数量达到上限，该策略会把任务队列中的任务放在调用者线程当中运行；
 *      DiscardOldestPolicy策略：该策略会丢弃任务队列中最老的一个任务，也就是当前任务队列中最先被添加进去的，马上要被执行的那个任务，并尝试再次提交；
 *      DiscardPolicy策略：该策略会默默丢弃无法处理的任务，不予任何处理。当然使用此策略，业务场景中需允许任务的丢失；
 */
public class RejectedExecutionDemo {
    private static ExecutorService pool;
    public static void main(String[] args) {
        //自定义拒绝策略
        // pool = new ThreadPoolExecutor(1, 2, 2000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5),
        //         Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
        //     @Override
        //     public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        //         System.out.println(r.toString()+"执行了拒绝策略");
        //
        //     }
        // });
        pool = new ThreadPoolExecutor(1, 2, 1000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());


        for (int i = 0; i < 10; i++) {
            pool.execute(new ThreadTaskCom(i));
        }

        // for (int i = 0; i < 10; i++) {
        //     pool.execute(new ThreadTaskSleep());
        // }
    }
}
