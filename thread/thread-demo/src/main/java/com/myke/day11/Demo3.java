package com.myke.day11;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 线程阻塞状态中断
 *
 * @author: zh
 * @date: 2020/4/25/025 11:12
 */
@Slf4j
public class Demo3 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new T1());
        thread.setName("thread-2");

        thread.start();

        TimeUnit.SECONDS.sleep(3);

        thread.interrupt();
    }

    public static class T1 implements Runnable {

        @Override
        public void run() {
            while (true) {
                //处理业务
                try {
                    //模拟阻塞
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    //InterruptedException异常，
                    //会清除线程内部的中断标志（即将中断标志置为false）
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                // 判断线程标志位，并将标志位重置为 false
                if (Thread.interrupted()) {
                    log.info("[{}]退出了", Thread.currentThread().getName());
                    break;
                }
            }
        }
    }
}
