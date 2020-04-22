package com.myke.day8;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 线程组 测试
 *
 * @author: zh
 * @date: 2020/4/22/022 22:31
 */
@Slf4j
public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup1 = new ThreadGroup("thread-group-1");

        new Thread(threadGroup1, new R1(), "thread-1").start();

        new Thread(threadGroup1, new R1(), "thread-2").start();

        TimeUnit.SECONDS.sleep(1);

        // activeCount() 可以返回线程组中的所有活动线程数，包含下面的所有子孙节点的线程，
        // 由于线程组中的线程是动态变化的，这个值只能是一个估算值
        log.info("threadGroup1 活动线程数 [{}]", threadGroup1.activeCount());
        log.info("threadGroup1 活动线程组 [{}]", threadGroup1.activeGroupCount());
        log.info("threadGroup1 线程组名称 [{}]", threadGroup1.getName());
        log.info("threadGroup1 父线程组名称 [{}]", threadGroup1.getParent().getName());
        log.info("threadGroup1 根线程组名称 [{}]", threadGroup1.getParent().getParent().getName());
        log.info("===============================================>>");

        //为线程组指定父线程组
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "thread-group-2");
        new Thread(threadGroup2, new R1(), "thread-3").start();
        new Thread(threadGroup2, new R1(), "thread-4").start();

        TimeUnit.SECONDS.sleep(2);

        log.info("threadGroup2 活动线程数 [{}]", threadGroup2.activeCount());
        log.info("threadGroup2 活动线程组 [{}]", threadGroup2.activeGroupCount());
        log.info("threadGroup2 线程组名称 [{}]", threadGroup2.getName());
        log.info("threadGroup2 父线程组名称 [{}]", threadGroup2.getParent().getName());

        log.info("===============================================>>");
        log.info("threadGroup1 活动线程数 [{}]", threadGroup1.activeCount());
        log.info("threadGroup1 活动线程组 [{}]", threadGroup1.activeGroupCount());

        //线程组的list()方法，将线程组中的所有子孙节点信息输出到控制台，用于调试使用
        threadGroup1.list();

        //批量停止线程
        threadGroup1.interrupt();
        TimeUnit.SECONDS.sleep(2);

        threadGroup1.list();
    }

    public static class R1 implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            log.info("所属线程组 [{}],线程名称:[{}]",
                    thread.getThreadGroup().getName(),
                    thread.getName());

            while (!thread.isInterrupted()) {

            }
            log.info("线程 [{}] 停止了", thread.getName());
        }
    }
}
