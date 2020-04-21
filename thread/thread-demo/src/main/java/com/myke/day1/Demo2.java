package com.myke.day1;

import java.util.concurrent.*;

public class Demo2 {

    //由于线程池是一个线程，AnotherCallable得不到执行，而被饿死，最终导致了程序死锁的现象。
    private static ExecutorService single = Executors.newSingleThreadExecutor();


    /**
     * 饥饿死锁
     * <p>
     * 因为有线程池只有一个线程,而在 main 线程中线程池已提交了任务MyCallable,
     * 而 MyCallable 任务中又使用了线程池提交任务AnotherCallable,这时这个任务是不能被执行的
     *
     * @param args
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable task = new MyCallable();
        //提交任务，异步获取结果
        Future<String> submit = single.submit(task);
        //线程处于等待中
        System.out.println(submit.get());
        System.out.println("over");
        single.shutdown();

    }


    public static class AnotherCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("in AnotherCallable");
            return "annother success";
        }
    }

    public static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("in MyCallable");
            //提交任务，异步获取结果
            Future<String> submit = single.submit(new AnotherCallable());
            //处于等待中，等待获取结果
            return "success:" + submit.get();
        }
    }
}