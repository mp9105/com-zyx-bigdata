package com.zyx.javademo.juc.threaddemo;

import java.util.concurrent.TimeUnit;

/**
 * @author zyx
 * @since 2021/5/5 07:50
 * desc: 生产者消费者案例
 */
public class ProdConsDemo {
    public static void main(String[] args) {
        // 商品初始质量为15kg
        Product product = new Product("prod1001", 15);
        // 生产者线程
        new ProducerThread(product, "producer1").start();
        new ProducerThread(product, "producer2").start();
        new ProducerThread(product, "producer3").start();
        // 消费者线程
        new ConsumerThread(product, "consumer1").start();
        new ConsumerThread(product, "consumer2").start();
    }
}

/**
 * 商品类
 */
class Product {
    /**
     * 商品ID
     */
    private String productId;
    /**
     * 剩余重量
     */
    private int leftWeight;

    public Product(String productId, int lastWeight) {
        this.productId = productId;
        this.leftWeight = lastWeight;
    }

    /**
     * 生产商品的方法(为简洁代码,在方法上加锁)
     *  当商品剩余大于20kg时, 等待消费
     *  当商品剩余小于20kg时, 进行生产
     */
    public synchronized void produce() {
        try {
            String name = Thread.currentThread().getName();
            // 商品数量小于等于20kg, 则进行生产, 每次生产2kg商品
            if (this.leftWeight <= 20) {
                this.leftWeight += 2;
                System.out.println(name + "生产了2kg商品,当前商品剩余" + this.leftWeight + "kg.");
            }
            // 唤醒其他人
            this.notifyAll();
            // 等待自己
            this.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费商品的方法
     *  当商品剩余大于等于3kg时(每次消费3kg), 等待消费
     *  否则, 等待生产者生产后进行消费
     */
    public synchronized void consumer() {
        try {
            String name = Thread.currentThread().getName();
            // 当商品剩余大于等于3kg时, 进行消费, 每次消费3kg
            if (this.leftWeight >= 3) {
                this.leftWeight -= 3;
                System.out.println(name + "消费了3kg商品,当前商品剩余" + this.leftWeight + "kg.");
            }
            // 唤醒其他人
            notifyAll();
            // 等待自己
            wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getLeftWeight() {
        return leftWeight;
    }

    public void setLeftWeight(int leftWeight) {
        this.leftWeight = leftWeight;
    }
}

/**
 * 生产者线程类
 */
class ProducerThread extends Thread {
    private final Product product;
    /**
     * 构造方法
     * @param product 商品
     * @param name 线程名称
     */
    public ProducerThread(Product product, String name) {
        super(name);
        this.product = product;
    }

    @Override
    public void run() {
        try {
            // 每隔2s生产一次
            while (true) {
                TimeUnit.SECONDS.sleep(2);
                product.produce();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * 消费者线程类
 */
class ConsumerThread extends Thread {
    private final Product product;
    /**
     * 构造方法
     * @param product 商品
     * @param name 线程名称
     */
    public ConsumerThread(Product product, String name) {
        super(name);
        this.product = product;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 每隔3秒消费一次
                TimeUnit.SECONDS.sleep(3);
                product.consumer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}