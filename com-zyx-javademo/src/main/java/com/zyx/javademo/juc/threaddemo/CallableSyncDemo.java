package com.zyx.javademo.juc.threaddemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author zyx
 * @since 2021/5/5 16:26
 * desc:通过Callable接口实现异步的加法案例
 */
public class CallableSyncDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // public FutureTask(Callable<V> callable)
        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallableThread());
        Thread t1 = new Thread(futureTask, "thread1");
        t1.start();

        int res01 = 100;
        // FutureTask的get方法拿到结果之前将会阻塞, 建议放在最后
        int res02 = futureTask.get();

        // futureTask.isDone() --> 判断是否计算完成

        System.out.println("result >>> " + (res01 + res02));
    }
}

class MyCallThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " >>> come in callable");
        TimeUnit.SECONDS.sleep(3L);
        return 1024;
    }
}