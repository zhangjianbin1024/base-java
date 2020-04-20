package com.dongnaoedu.completionService;

import java.util.concurrent.*;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/10
 * 创建时间: 21:57
 */
public class CompletionTest5 {
    private final int POOL_SIZE = 5;//线程池的大小
    private final int TOTAL_TASK = 10;// 任务个数

    // 方法一，自己写集合来实现获取线程池中任务的返回结果
    public void testByQueue() throws InterruptedException, ExecutionException {

        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

        BlockingQueue<Future<String>> queue = new LinkedBlockingDeque<>();
        // 向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<String> future = pool.submit(new WorkTask("ExecTask" + i));
            queue.add(future);
        }

        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            // 当前任务结果
            System.out.println("ExecTask:" + queue.take().get());
        }

        pool.shutdown();
        System.out.println("线程执行完毕");

    }

    // 方法二，通过CompletionService来实现获取线程池中任务的返回结果
    public void testByCompletion() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

        // 线程池传给 ExecutorCompletionService
        CompletionService<String> service = new ExecutorCompletionService<String>(pool);

        // 向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++) {
            service.submit(new WorkTask("ExecTask" + i));
        }

        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<String> future = service.take();
            System.out.println("CompletionService:" + future.get());
        }

        System.out.println("线程执行完毕");

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletionTest5 completionTest = new CompletionTest5();

        //通过CompletionService来实现获取线程池中任务的返回结果
        // 任务先执行完的结果，先拿出来
        completionTest.testByCompletion();

        //自己写集合来实现获取线程池中任务的返回结果
        //completionTest.testByQueue();
    }

}
