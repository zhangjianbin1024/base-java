package com.myke.day16;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TaskDisposeUtils是一个并行处理的工具类，
 * 可以传入n个任务内部使用线程池进行处理，等待所有任务都处理完成之后，方法才会返回
 */
@Slf4j
public class TaskDisposeUtils {
    //并行线程数
    public static int POOL_SIZE = 0;

    static {
        POOL_SIZE = Integer.max(Runtime.getRuntime().availableProcessors(), 5);
    }

    public static <T> void dispose(List<T> taskList, Consumer<T> consumer) {
        dispose(true, POOL_SIZE, taskList, consumer);
    }

    public static <T> void dispose(boolean moreThread, int poolSize, List<T> taskList, Consumer<T> consumer) {
        if (taskList.isEmpty()) {
            return;
        }
        if (moreThread && poolSize > 1) {
            poolSize = Math.min(poolSize, taskList.size());

            ExecutorService executorService = null;

            try {
                executorService = Executors.newFixedThreadPool(poolSize);

                CountDownLatch countDownLatch = new CountDownLatch(taskList.size());

                for (T item : taskList) {
                    executorService.execute(() -> {
                        try {
                            consumer.accept(item);
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
                }

                try {
                    //等待 list 任务都执行完成后，程序才继续往下执行
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                if (executorService != null) {
                    log.info("关闭线程池资源");
                    executorService.shutdown();
                }
            }
        } else {
            for (T item : taskList) {
                consumer.accept(item);
            }
        }
    }


    public static void main(String[] args) {

        //生成1-10的10个数字，放在list中，相当于10个任务
        List<Integer> collect = Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList());

        //启动多线程处理list中的数据，每个任务休眠时间为list中的数值
        TaskDisposeUtils.dispose(collect, item -> {

            try {
                long startTime = System.currentTimeMillis();
                TimeUnit.SECONDS.sleep(item);
                long endTime = System.currentTimeMillis();

                log.info("[{}],任务：[{}],执行完毕,耗时:[{}]",
                        System.currentTimeMillis(),
                        item, endTime - startTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //上面所有任务处理完毕完毕之后，程序才能继续
        log.info("[{}] 中的任务都处理完毕", collect);

    }
}