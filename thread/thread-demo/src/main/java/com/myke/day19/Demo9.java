package com.myke.day19;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 启动一个线程来获取执行结果
 * <p>
 * FutureTask除了实现Future接口，还实现了Runnable接口，
 * 因此FutureTask可以交给Executor执行，也可以交给线程执行执行
 */
public class Demo9 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> futureTask = new FutureTask<Integer>(() -> {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",start!");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",end!");
            return 10;
        });

        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName());

        //启动一个线程
        new Thread(futureTask).start();
        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName());
        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",结果:" + futureTask.get());
    }
}