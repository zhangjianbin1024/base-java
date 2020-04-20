package com.dongnaoedu;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/23
 * 创建时间: 14:44
 * <p>
 * ConcurrentLinkedQueue  无界非阻塞队列
 * <p>
 * isEmpty()和size()的性能差异
 */
public class ConcurrentLinkedQueueTest2 {
    private static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
    private static int count = 50000;
    private static int count2 = 2;
    private static CountDownLatch cd = new CountDownLatch(count2);

    public static void dothis() {
        for (int i = 0; i < count; i++) {
            //添加元素
            queue.offer(i);
        }
    }

    //Poll : get头元素把元素拿走
    static class Poll implements Runnable {
        @Override
        public void run() {
            // 多用 isEmpty() ,少用size，size 消耗性能特别大

            //while (queue.size()>0) {
            while (!queue.isEmpty()) {
                System.out.println(queue.poll());
            }
            cd.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long timeStart = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(4);
        ConcurrentLinkedQueueTest2.dothis();
        //启用两个线程取数据
        for (int i = 0; i < count2; i++) {
            es.submit(new Poll());
        }
        cd.await();
        System.out.println("cost time "
                + (System.currentTimeMillis() - timeStart) + "ms");
        es.shutdown();
    }
}
