package com.myke.day17;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrier相当于使用了2次，
 * <p>
 * 第一次用于等待所有人到达后开饭，
 * 第二次用于等待所有人上车后驱车去下一景点
 */
@Slf4j
public class Demo2 {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new T("员工" + i, i).start();
        }
    }


    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

    public static class T extends Thread {
        int sleep;

        public T(String name, int sleep) {
            super(name);
            this.sleep = sleep;
        }


        public void eat() {
            try {
                TimeUnit.SECONDS.sleep(sleep);
                long startTime = System.currentTimeMillis();

                // 调用await()的时候，当前线程将会被阻塞，
                // 每调用一次await()方法都将使阻塞的线程数+1，
                // 只有阻塞的线程数达到设定值时屏障才会打开，允许阻塞的所有线程继续执行
                cyclicBarrier.await();
                long endStime = System.currentTimeMillis();
                log.info("[{}],sleep 等待了 [{}] (ms),开始吃饭了", this.getName(), endStime - startTime);

                //休眠sleep时间，模拟当前员工吃饭耗时
                TimeUnit.SECONDS.sleep(sleep);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        //等待所有人到齐之后，开车去下一站
        public void drive() {
            try {
                TimeUnit.SECONDS.sleep(sleep);
                long startTime = System.currentTimeMillis();

                // 调用await()的时候，当前线程将会被阻塞，
                // 每调用一次await()方法都将使阻塞的线程数+1，
                // 只有阻塞的线程数达到设定值时屏障才会打开，允许阻塞的所有线程继续执行
                cyclicBarrier.await();
                long endStime = System.currentTimeMillis();
                log.info("[{}],sleep 等待了 [{}] (ms),去下一景点的路上", this.getName(), endStime - startTime);


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            //等待所有人到齐之后吃饭，先到的人坐那等着，什么事情不要干
            this.eat();
            //等待所有人到齐之后开车去下一景点，先到的人坐那等着，什么事情不要干
            this.drive();
        }
    }
}