package com.zyx.javademo.juc.threadpool;

/**
 * @author Yaxi.Zhang
 * @since 2021/4/23 15:51
 * desc:
 */
public class ThreadTaskCom implements Runnable, Comparable<ThreadTaskCom> {
    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ThreadTaskCom(int priority) {
        this.priority = priority;
    }


    @Override
    public int compareTo(ThreadTaskCom o) {
        //当前对象和其他对象做比较，当前优先级大就返回-1，优先级小就返回1,值越小优先级越高
        return this.priority > o.priority ? -1 : 1;
    }

    @Override
    public void run() {
        try {
            // 让线程阻塞，使后续任务进入缓存队列
            Thread.sleep(1000);
            System.out.println("priority:" + this.priority + ",ThreadName:" + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
