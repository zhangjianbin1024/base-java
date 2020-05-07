package com.myke.day18;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 扩展线程池
 */
public class Demo6 {
    static class Task implements Runnable {
        String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "处理" + this.name);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                10,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                Executors.defaultThreadFactory(),
                (r, executors) -> {
                    //自定义饱和策略
                    //记录一下无法处理的任务
                    System.out.println("无法处理的任务：" + r.toString());
                }) {

            /**
             * 任务执行之前调用的方法，有2个参数，第1个参数是执行任务的线程，第2个参数是任务
             * @param t
             * @param r
             */
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println(System.currentTimeMillis() + "," + t.getName() + ",开始执行任务:" + r.toString());
            }

            /**
             * afterExecute：任务执行完成之后调用的方法，2个参数，
             * 第1个参数表示任务，
             * 第2个参数表示任务执行时的异常信息，
             * 如果无异常，第二个参数为null
             * @param r
             * @param t
             */
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",任务:" + r.toString() + "，执行完毕!");
            }

            /**
             * 线程池最终关闭之后调用的方法。所有的工作线程都退出了，最终线程池会退出，退出时调用该方法
             */
            @Override
            protected void terminated() {
                System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，关闭线程池!");
            }
        };


        for (int i = 0; i < 10; i++) {
            executor.execute(new Task("任务-" + i));
        }
        TimeUnit.SECONDS.sleep(1);
        executor.shutdown();
    }
}