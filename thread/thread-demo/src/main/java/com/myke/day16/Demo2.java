package com.myke.day16;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch  测试
 */
@Slf4j
public class Demo2 {
    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis();

        //创建了计数器为2的 CountDownLatch
        CountDownLatch countDownLatch = new CountDownLatch(2);

        T sheet1 = new T("解析sheet1", 2, countDownLatch);
        sheet1.start();

        T sheet2 = new T("解析sheet2", 5, countDownLatch);
        sheet2.start();

        //会让主线程等待 sheet1、sheet2
        countDownLatch.await();

        long endTime = System.currentTimeMillis();

        log.info("总耗时：[{}]", endTime - startTime);
    }


    public static class T extends Thread {
        //休眠时间
        private int sleepSeconds;
        private CountDownLatch countDownLatch;

        public T(String name, int sleepSeconds, CountDownLatch countDownLatch) {
            super(name);
            this.sleepSeconds = sleepSeconds;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            log.info("[{}],[{}],开始处理", startTime, this.getName());

            try {
                //模拟耗时操作，休眠sleepSeconds秒
                TimeUnit.SECONDS.sleep(this.sleepSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 此方法每调用一次，CountDownLatch 内部计数器会减1，
                // 当计数器变为0的时候，主线程中的await()会返回，然后继续执行
                countDownLatch.countDown();
            }

            log.info("[{}],[{}],处理完毕,耗时：[{}]", startTime,
                    this.getName(),
                    System.currentTimeMillis() - startTime);
        }
    }
}