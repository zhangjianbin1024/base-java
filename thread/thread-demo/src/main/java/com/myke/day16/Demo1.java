package com.myke.day16;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * join 测试
 */
@Slf4j
public class Demo1 {
    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis();
        T sheet1 = new T("解析sheet1", 2);
        sheet1.start();

        T sheet2 = new T("解析sheet2", 5);
        sheet2.start();

        sheet1.join();
        sheet2.join();

        long endTime = System.currentTimeMillis();

        log.info("总耗时：[{}]", endTime - startTime);
    }


    public static class T extends Thread {
        //休眠时间
        private int sleepSeconds;

        public T(String name, int sleepSeconds) {
            super(name);
            this.sleepSeconds = sleepSeconds;
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
            }

            log.info("[{}],[{}],处理完毕,耗时：[{}]", startTime,
                    this.getName(),
                    System.currentTimeMillis() - startTime);
        }
    }
}