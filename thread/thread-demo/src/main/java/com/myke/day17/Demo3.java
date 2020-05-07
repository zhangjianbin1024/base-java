package com.myke.day17;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 员工10是最后到达的，让所有人都久等了，那怎么办，得给所有人倒酒，然后开饭
 */
@Slf4j
public class Demo3 {

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new T("员工" + i, i).start();
        }
    }

    // 倒酒操作是由最后一个人操作的，最后一个人倒酒完毕之后，
    // 才唤醒所有等待中的其他员工，让大家开饭
    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
        try {
            //模拟倒酒，花了2秒，又得让其他9个人等2秒
            TimeUnit.SECONDS.sleep(2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("[{}]说，不好意思，让大家久等了，给大家倒酒赔罪!", Thread.currentThread().getName());

    });


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