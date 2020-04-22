package com.myke.day6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 线程强制停止
 */
@Slf4j
public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                log.info("start");
                boolean flag = true;
                while (flag) {

                }
                log.info("end");
            }
        };

        thread.setName("thread-1");
        thread.start();

        //当前线程休眠 1 秒
        TimeUnit.SECONDS.sleep(1);

        //线程停止
        thread.stop();

        log.info("线程状态：[{}]", thread.getState());

        TimeUnit.SECONDS.sleep(1);

        log.info("线程状态：[{}]", thread.getState());

    }
}