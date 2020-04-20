package com.dongnaoedu.bq;

/**
 * Created by Administrator on 2017/8/17/017.
 * <p>
 * lock 能过通知机制实现有界阻塞队列 测试
 */
public class BqTest5 {
    public static void main(String[] args) {
        BlockingQueueLC<Integer> bq = new BlockingQueueLC(10);
        Thread threadA = new ThreadPush(bq);
        threadA.setName("Push");

        Thread threadB = new ThreadPop(bq);
        threadB.setName("Pop");

        threadB.start();
        threadA.start();
    }

    private static class ThreadPush extends Thread {
        BlockingQueueLC<Integer> bq;

        public ThreadPush(BlockingQueueLC<Integer> bq) {
            this.bq = bq;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            int i = 20;
            while (i > 0) {
                try {
                    Thread.sleep(500);
                    System.out.println(" i=" + i + " will push");
                    bq.enqueue(i--);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }

            }
        }
    }

    private static class ThreadPop extends Thread {
        BlockingQueueLC<Integer> bq;

        public ThreadPop(BlockingQueueLC<Integer> bq) {
            this.bq = bq;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 出队比入队慢
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " will pop.....");
                    Integer i = bq.dequeue();
                    System.out.println(" i=" + i.intValue() + " alread pop");
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }

        }
    }
}
