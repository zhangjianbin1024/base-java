package com.myke.day19;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用ScheduleThreadPoolExecutor的schedule方法
 * <p>
 * schedule:延迟执行任务1次
 */
public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        scheduledExecutorService.schedule(() -> {
                    System.out.println(System.currentTimeMillis() + "开始执行");
                    //模拟任务耗时
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(System.currentTimeMillis() + "执行结束");
                },
                2,
                TimeUnit.SECONDS);
    }
}