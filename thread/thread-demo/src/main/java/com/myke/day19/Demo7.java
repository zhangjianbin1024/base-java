package com.myke.day19;

import java.util.concurrent.*;

/**
 * 取消正在执行的任务
 */
public class Demo7 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<Integer> result = executorService.submit(() -> {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",start!");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",end!");
            return 10;
        });

        executorService.shutdown();

        TimeUnit.SECONDS.sleep(1);

        //取消正在执行的任务，参数表示是否对执行的任务发送中断信号
        result.cancel(false);

        //用来判断任务是否被取消
        System.out.println("任务是否被取消: " + result.isCancelled());
        //判断任务是否执行完毕
        System.out.println("任务是否执行完毕: " + result.isDone());

        TimeUnit.SECONDS.sleep(5);
        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName());

        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",结果：" + result.get());

        executorService.shutdown();
    }
}