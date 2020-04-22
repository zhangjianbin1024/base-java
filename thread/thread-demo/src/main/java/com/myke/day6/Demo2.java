package com.myke.day6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 线程停止，中断测试
 */
@Slf4j
public class Demo2 {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        //重设中断标志位为true
                        this.interrupt();

                        e.printStackTrace();
                    }
                    if (this.isInterrupted()) {
                        log.info("线程退出了");
                        break;
                    }
                }
            }
        };

        thread.setName("thread-1");
        thread.start();

        TimeUnit.SECONDS.sleep(1);

        //发出线程中断信息
        thread.interrupt();
    }
}