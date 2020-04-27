package com.myke.day11;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 通过线程自带的中断标志控制
 *
 * @author: zh
 * @date: 2020/4/25/025 11:07
 */
@Slf4j
public class Demo2 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new T1());
        thread.setName("thread-2");
        thread.start();

        TimeUnit.SECONDS.sleep(3);

        //线程的中断标志会被置为true
        thread.interrupt();
    }

    public static class T1 implements Runnable {
        @Override
        public void run() {
            while (true) {
                //获取线程的中断标志
                if (Thread.interrupted()) {
                    log.info("[{}]退出了", Thread.currentThread().getName());
                    break;
                }
            }
        }
    }
}
