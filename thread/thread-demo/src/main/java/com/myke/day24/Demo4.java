package com.myke.day24;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * InheritableThreadLocal 使用
 */
public class Demo4 {

    //创建一个操作Thread中存放请求任务追踪id口袋的对象
    //static ThreadLocal<String> traceIdKD = new ThreadLocal<>();

    //inheritableThreadLocals相当于线程中另外一种兜，这种兜有什么特征呢，
    //当创建子线程的时候，子线程会将父线程这种类型兜的东西全部复制一份放到自己的inheritableThreadLocals兜中，
    //使用InheritableThreadLocal对象可以操作线程中的inheritableThreadLocals兜。

    //使用InheritableThreadLocal解决上面子线程中无法输出traceId的问题
    static ThreadLocal<String> traceIdKD = new InheritableThreadLocal<>();

    static AtomicInteger threadIndex = new AtomicInteger(1);

    //创建处理请求的线程池子
    static ThreadPoolExecutor disposeRequestExecutor = new ThreadPoolExecutor(
            3,
            3,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("disposeRequestThread-" + threadIndex.getAndIncrement());
                return thread;
            });

    //记录日志
    public static void log(String msg) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        //获取当前线程存放tranceId口袋中的内容
        String traceId = traceIdKD.get();
        System.out.println("****" + System.currentTimeMillis() + "[traceId:" + traceId + "],[线程:" + Thread.currentThread().getName() + "]," + stack[1] + ":" + msg);
    }

    //模拟controller
    public static void controller(List<String> dataList) {
        log("接受请求");
        service(dataList);
    }

    //模拟service
    public static void service(List<String> dataList) {
        log("执行业务");
        dao(dataList);
    }

    //模拟dao
    public static void dao(List<String> dataList) {
        CountDownLatch countDownLatch = new CountDownLatch(dataList.size());

        log("执行数据库操作");
        String threadName = Thread.currentThread().getName();
        //模拟插入数据
        for (String s : dataList) {
            // 多线程处理 dataList ,这时的 新的线程输出的日志中是拿不到 traceId的,输出的 traceId为null
            new Thread(() -> {
                try {
                    //模拟数据库操作耗时100毫秒
                    TimeUnit.MILLISECONDS.sleep(100);
                    log("插入数据" + s + "成功,主线程：" + threadName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }, "dataList-" + s).start();
        }
        //等待上面的dataList处理完毕
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //需要插入的数据
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dataList.add("数据" + i);
        }

        //模拟5个请求
        int requestCount = 5;
        for (int i = 0; i < requestCount; i++) {
            String traceId = String.valueOf(i);
            disposeRequestExecutor.execute(() -> {
                //把traceId放入口袋中
                traceIdKD.set(traceId);
                try {
                    controller(dataList);
                } finally {
                    //将tranceId从口袋中移除
                    traceIdKD.remove();
                }
            });
        }

        disposeRequestExecutor.shutdown();
    }
}