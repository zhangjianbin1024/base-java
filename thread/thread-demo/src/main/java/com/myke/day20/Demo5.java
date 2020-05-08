package com.myke.day20;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * jdb api 版
 * <p>
 * 异步执行一批任务，有一个完成立即返回，其他取消
 */
public class Demo5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Callable<Integer>> list = new ArrayList<>();
        int taskCount = 5;
        for (int i = taskCount; i > 0; i--) {
            int j = i * 2;
            String taskName = "任务" + i;
            list.add(() -> {
                TimeUnit.SECONDS.sleep(j);
                System.out.println(taskName + "执行完毕!");
                return j;
            });
        }

        Integer integer = executorService.invokeAny(list);

        System.out.println("耗时:" + (System.currentTimeMillis() - startime) + ",执行结果:" + integer);
        executorService.shutdown();
    }
}