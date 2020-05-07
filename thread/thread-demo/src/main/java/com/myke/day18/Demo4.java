package com.myke.day18;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义创建线程的工厂
 */
public class Demo4 {
    static AtomicInteger threadNum = new AtomicInteger(1);

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5,
                5,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10), r -> {
            Thread thread = new Thread(r);
            thread.setName("自定义线程-" + threadNum.getAndIncrement());
            return thread;
        });

        for (int i = 0; i < 5; i++) {
            String taskName = "任务-" + i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "处理" + taskName);
            });
        }
        executor.shutdown();
    }
}