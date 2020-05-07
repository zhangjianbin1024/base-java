package com.myke.day19;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * scheduleWithFixedDelay:固定的间隔执行任务
 * <p>
 * 下一次执行时间是上一次任务执行完的系统时间加上period，
 * 因而具体执行时间不是固定的，但周期是固定的，是采用相对固定的延迟来执行任务
 * <p>
 * 第1次：T1+initialDelay，执行结束时间：E1
 * 第2次：E1+period，执行结束时间：E2
 * 第3次：E2+period，执行结束时间：E3
 * 第4次：E3+period，执行结束时间：E4
 * 第n次：上次执行结束时间+period
 */
public class Demo3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());
        //任务执行计数器
        AtomicInteger count = new AtomicInteger(1);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        //延迟1秒之后执行第1次，后面每次的执行时间和上次执行结束时间间隔3秒。
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
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
                1, //表示延迟多久执行第一次
                3, //表示下次执行时间和上次执行结束时间之间的间隔时间
                TimeUnit.SECONDS);
    }
}