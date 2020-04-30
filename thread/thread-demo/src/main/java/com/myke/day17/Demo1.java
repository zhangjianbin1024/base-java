package com.myke.day17;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 模拟了10个员工上桌吃饭的场景，等待所有员工都到齐了才能吃饭
 */
@Slf4j
public class Demo1 {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new T("员工" + i, i).start();
        }


    }

    //CyclicBarrier通常称为循环屏障
    //CyclicBarrier只是使等待的线程达到一定数目后再让它们继续往后执行
    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

    public static class T extends Thread {
        int sleep;

        public T(String name, int sleep) {
            super(name);
            this.sleep = sleep;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(sleep);
                long startTime = System.currentTimeMillis();

                // 调用await()的时候，当前线程将会被阻塞，
                // 每调用一次await()方法都将使阻塞的线程数+1，
                // 只有阻塞的线程数达到设定值时屏障才会打开，允许阻塞的所有线程继续执行
                cyclicBarrier.await();

                long endStime = System.currentTimeMillis();

                log.info("[{}],sleep 等待了 [{}] (ms),开始吃饭了", this.getName(), endStime - startTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}