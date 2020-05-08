package com.myke.day20;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * 执行一批任务，然后消费执行结果
 */
public class Demo3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Callable<Integer>> list = new ArrayList<>();
        int taskCount = 5;
        for (int i = taskCount; i > 0; i--) {
            int j = i * 2;
            list.add(() -> {
                TimeUnit.SECONDS.sleep(j);
                return j;
            });
        }

        solve(executorService, list, a -> {
            System.out.println(System.currentTimeMillis() + ":" + a);
        });
        executorService.shutdown();
    }

    //传入了一批任务进行处理，最终将所有处理完成的按任务完成的先后顺序传递给Consumer进行消费了。
    public static <T> void solve(Executor e, Collection<Callable<T>> solvers, Consumer<T> use) throws InterruptedException, ExecutionException {
        CompletionService<T> ecs = new ExecutorCompletionService<T>(e);
        //提交任务
        for (Callable<T> s : solvers) {
            ecs.submit(s);
        }
        int n = solvers.size();
        for (int i = 0; i < n; ++i) {
            //take 从服务中返回并移除一个已经完成的任务，如果获取不到，会一致阻塞到有返回值为止
            T r = ecs.take().get();
            if (r != null) {
                use.accept(r);
            }
        }
    }
}