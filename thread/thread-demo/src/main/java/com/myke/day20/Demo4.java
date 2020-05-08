package com.myke.day20;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * 异步执行一批任务，有一个完成立即返回，其他取消
 */
public class Demo4 {
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
        Integer integer = invokeAny(executorService, list);
        System.out.println("耗时:" + (System.currentTimeMillis() - startime) + ",执行结果:" + integer);
        executorService.shutdown();
    }


    public static <T> T invokeAny(Executor e, Collection<Callable<T>> solvers) throws InterruptedException, ExecutionException {
        CompletionService<T> ecs = new ExecutorCompletionService<T>(e);

        //任务集合
        List<Future<T>> futureList = new ArrayList<>();
        // 提交任务
        for (Callable<T> s : solvers) {
            futureList.add(ecs.submit(s));
        }

        int n = solvers.size();
        try {
            for (int i = 0; i < n; ++i) {
                //取出任务执行结果
                T r = ecs.take().get();
                if (r != null) {
                    return r;
                }
            }
        } finally {
            for (Future<T> future : futureList) {
                // 对所有任务发送取消操作
                future.cancel(true);
            }
        }
        return null;
    }
}