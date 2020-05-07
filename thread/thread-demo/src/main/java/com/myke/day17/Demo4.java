package com.myke.day17;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 其中一个人等待中被打断了
 * <p>
 * 结论
 * 1. 内部有一个人把规则破坏了（接收到中断信号），其他人都不按规则来了，不会等待了
 * 2. 接收到中断信号的线程，await方法会触发InterruptedException异常，然后被唤醒向下运行
 * 3. 其他等待中 或者后面到达的线程，会在await()方法上触发`BrokenBarrierException`异常，然后继续执行
 */
@Slf4j
public class Demo4 {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            int sleep = 0;
            if (i == 10) {
                sleep = 10;
            }
            T t = new T("员工" + i, sleep);
            t.start();
            if (i == 5) {
                //模拟员工5接了个电话，将自己等待吃饭给打断了
                TimeUnit.SECONDS.sleep(1);
                log.info("[{}],有点急事，我先开干了！", t.getName());
                t.interrupt();
                TimeUnit.SECONDS.sleep(2);
            }
        }
    }

    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);


    public static class T extends Thread {
        int sleep;

        public T(String name, int sleep) {
            super(name);
            this.sleep = sleep;
        }

        @Override
        public void run() {
            long startTime = 0L;
            try {
                TimeUnit.SECONDS.sleep(sleep);
                startTime = System.currentTimeMillis();
                log.info("[{}] 到了！", this.getName());

                // 调用await()的时候，当前线程将会被阻塞，
                // 每调用一次await()方法都将使阻塞的线程数+1，
                // 只有阻塞的线程数达到设定值时屏障才会打开，允许阻塞的所有线程继续执行
                cyclicBarrier.await();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }


            long endStime = System.currentTimeMillis();
            log.info("[{}],sleep 等待了 [{}] (ms),开始吃饭了", this.getName(), endStime - startTime);
        }
    }

}