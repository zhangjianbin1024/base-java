package com.dongnaoedu.tooluse;

import java.util.concurrent.CountDownLatch;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/07
 * 创建时间: 20:19
 * <p>
 * CountDownLatch.await 一般阻塞主线程，所有的工作线程执行countDown
 */
public class CountDownLatchCase6 {

    static CountDownLatch c = new CountDownLatch(7);

    private static class SubThread implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getId());
            c.countDown(); // 会减一
            System.out.println(Thread.currentThread().getId() + " is done");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId());
                c.countDown();
                System.out.println("sleeping...");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("sleep is completer");
                c.countDown();
            }
        }).start();

        for (int i = 0; i <= 4; i++) {
            Thread thread = new Thread(new SubThread());
            thread.start();
        }

        c.await(); // await方法会阻塞当前线程，等待其它线程处理完毕了，才继续执行主线程
        System.out.println("Main will gone.....");
    }
}
