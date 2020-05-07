package com.myke.day19;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 定时任务有异常时，不会报出异常信息
 */
public class Demo4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());
        //任务执行计数器
        AtomicInteger count = new AtomicInteger(1);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
                    try {
                        int currCount = count.getAndIncrement();
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(System.currentTimeMillis() + "第" + currCount + "次" + "开始执行");

                        // 去掉 try catch 时会触发异常，发生异常之后没有任何现象，被 ScheduledExecutorService 内部给吞掉了，
                        // 然后这个任务再也不会执行了

                        //所以如果程序有异常，开发者自己注意处理一下，不然跑着跑着发现任务怎么不跑了，也没有异常输出。
                        System.out.println(10 / 0);

                        System.out.println(System.currentTimeMillis() + "第" + currCount + "次" + "执行结束");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                1,
                1,
                TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(5);

        // 如果此任务在正常完成之前被取消，则返回true
        System.out.println("任务是否被取消： " + scheduledFuture.isCancelled());

        // scheduledFuture.isDone()输出true，表示这个任务已经结束了,再也不会被执行了
        System.out.println("任务是否结束： " + scheduledFuture.isDone());

    }
}