package com.myke.day19;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());
        //任务执行计数器
        AtomicInteger count = new AtomicInteger(1);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
                    int currCount = count.getAndIncrement();
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(System.currentTimeMillis() + "第" + currCount + "次" + "开始执行");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(System.currentTimeMillis() + "第" + currCount + "次" + "执行结束");
                },
                1,
                1,
                TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(5);

        // 想取消执行，可以调用ScheduledFuture的cancel方法，参数表示是否给任务发送中断信号。
        scheduledFuture.cancel(false);

        TimeUnit.SECONDS.sleep(1);

        System.out.println("任务是否被取消：" + scheduledFuture.isCancelled());
        System.out.println("任务是否已完成：" + scheduledFuture.isDone());
    }
}