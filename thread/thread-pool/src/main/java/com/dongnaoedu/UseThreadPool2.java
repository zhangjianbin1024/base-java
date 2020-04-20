package com.dongnaoedu;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/07
 * 创建时间: 21:59
 */
public class UseThreadPool2 {

    static class MyTask implements Runnable {

        private String name;


        public MyTask(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {// 执行任务
            try {
                Random r = new Random();
                Thread.sleep(r.nextInt(1000) + 2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getId() + " sleep InterruptedException:"
                        + Thread.currentThread().isInterrupted());
            }
            System.out.println(Thread.currentThread().getName() + " 任务 " + name + " 完成");
        }
    }

    public static void main(String[] args) {
        //创建线程池
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(2, 4, 60,
                        TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new ThreadFactory() {
                    private final AtomicInteger threadNumber = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r, "custom-thread-" + threadNumber.getAndIncrement());
                        return thread;
                    }
                });

        for (int i = 0; i <= 5; i++) {
            MyTask task = new MyTask("Task_" + i);
            System.out.println("A new task will add:" + task.getName());

            // 提交任务给线程池执行,
            // execute：提交不需要返回值的任务；
            // submit：提交需要返回值任务，返回值是个Future类型的对象，调用future 的get方法（阻塞方法），获取返回值
            threadPoolExecutor.execute(task);

        }
        // 通过 interrupt方法来终止线程
        // shutDownNow() 尝试停止所有正在执行的线程，执行stop
        threadPoolExecutor.shutdown();
        //threadPoolExecutor.shutdownNow();

        // 当前机器中的cpu核心个数
        //System.out.println(Runtime.getRuntime().availableProcessors());

        // 使用工厂类Executors来创建线程池
        // 创建使用固定线程数的FixedThreadPool的API
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        MyTask task = new MyTask("Task_newFixedThreadPool");
        newFixedThreadPool.execute(task);
        newFixedThreadPool.shutdown();

        // 创建使用单个线程的SingleThread-Executor的API
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        // 会根据需要创建新线程的CachedThreadPool的API
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        // 创建一个工作窃取的线程池，使用forkjoin实现
        ExecutorService newWorkStealingPool = Executors.newWorkStealingPool();


    }


}
