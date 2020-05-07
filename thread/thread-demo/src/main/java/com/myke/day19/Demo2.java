package com.myke.day19;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * scheduleAtFixedRate:固定的频率执行任务
 * <p>
 * 该方法设置了执行周期，下一次执行时间相当于是上一次的执行时间加上period，
 * 任务每次执行完毕之后才会计算下次的执行时间。
 * <p>
 * 假设系统调用scheduleAtFixedRate的时间是T1，那么执行时间如下：
 * 第1次：T1+initialDelay
 * 第2次：T1+initialDelay+period
 * 第3次：T1+initialDelay+2*period
 * 第n次：T1+initialDelay+(n-1)*period
 */
public class Demo2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());

        //任务执行计数器
        AtomicInteger count = new AtomicInteger(1);

        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(10);

        //设置的任务第一次执行时间是系统启动之后延迟一秒执行。后面每次时间间隔1秒

        //任务当前执行完毕之后会计算下次执行时间，下次执行时间为上次执行的开始时间+period，
        // 这个时间小于第一次结束的时间了，说明小于系统当前时间了，会立即执行
        scheduledExecutorService.scheduleAtFixedRate(() -> {
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
    }
}