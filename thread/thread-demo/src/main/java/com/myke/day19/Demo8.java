package com.myke.day19;

import java.util.concurrent.*;

/**
 * 超时获取异步任务执行结果
 * <p>
 * 比如耗时1分钟，我最多只能等待10秒，如果10秒还没返回，我就去做其他事情了。
 */
public class Demo8 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<Integer> result = executorService.submit(() -> {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",start!");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",end!");
            return 10;
        });
        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName());

        try {
            //get方法获取执行结果，超时时间是3秒，3秒还未获取到结果，get触发了TimeoutException异常，当前线程从阻塞状态苏醒了
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",结果：" + result.get(3, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}