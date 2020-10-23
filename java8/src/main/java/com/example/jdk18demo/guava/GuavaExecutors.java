package com.example.jdk18demo.guava;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用Guava封装的线程池
 *
 * @Author: zhangjianbin
 * @Date: 2019/8/15 9:48
 */
public class GuavaExecutors {
    /**
     * 根据cpu的数量动态的配置核心线程数和最大线程数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 核心线程数 = CPU核心数 + 1
     */
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    /**
     * 线程池最大线程数 = CPU核心数 * 2 + 1
     */
    private static final int MAXIMUM_POOL_SIZE = CORE_POOL_SIZE * 2 + 1;

    /**
     * 非核心线程闲置时间 = 超时2s
     */
    private static final int KEEP_ALIVE = 2;

    private static ListeningExecutorService defaultCompletedExecutorService = null;

    private static final Object lock = new Object();

    public static ListeningExecutorService newCachedExecutorService(int maxThreadNumber, final String namePrefix) {
        return MoreExecutors.listeningDecorator(
                new ThreadPoolExecutor(CORE_POOL_SIZE, maxThreadNumber, KEEP_ALIVE, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<Runnable>(1000), new ThreadFactory() {

                    private final AtomicInteger poolNumber = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r, namePrefix + poolNumber.getAndIncrement());
                        return thread;
                    }
                }));

    }

    public static ListeningExecutorService newCachedExecutorService(String namePrefix) {
        return newCachedExecutorService(MAXIMUM_POOL_SIZE, namePrefix);
    }

    public static ListeningExecutorService getDefaultCompletedExecutorService() {
        if (defaultCompletedExecutorService == null) {
            synchronized (lock) {
                if (defaultCompletedExecutorService == null) {
                    defaultCompletedExecutorService = newCachedExecutorService("Completed-Callback-");
                }
            }
        }
        return defaultCompletedExecutorService;
    }
}