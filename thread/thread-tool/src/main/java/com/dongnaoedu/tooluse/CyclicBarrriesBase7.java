package com.dongnaoedu.tooluse;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/07
 * 创建时间: 20:30
 */
public class CyclicBarrriesBase7 {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId());
                try {
                    c.await();//等待主线程完成
                    System.out.println(Thread.currentThread().getId() + " is going");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("sleeping...");

            }
        }).start();

        System.out.println("main will sleep.....");
        Thread.sleep(2000);

        c.await();////等待子线程完成

        System.out.println("All are complete.");
    }


}
