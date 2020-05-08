package com.myke.day21;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用JUC中的类实现计数器
 * <p>
 * 通过原子操作可以保证数据在并发情况下的正确性。
 * 原子类操作，都位于java.util.concurrent.atomic包中
 * <p>
 * AtomicInteger内部是采用cas操作来保证对int类型数据增减操作在多线程情况下的正确性。
 */
public class Demo3 {
    //访问次数
    static AtomicInteger count = new AtomicInteger();

    //模拟访问一次
    public static void request() throws InterruptedException {
        //模拟耗时5毫秒
        TimeUnit.MILLISECONDS.sleep(5);
        //对count原子+1
        count.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        long starTime = System.currentTimeMillis();

        int threadSize = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);

        for (int i = 0; i < threadSize; i++) {
            Thread thread = new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        request();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }

        countDownLatch.await();

        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "，耗时：" + (endTime - starTime) + ",count=" + count);
    }
}