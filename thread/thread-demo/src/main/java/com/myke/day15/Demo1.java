package com.myke.day15;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore简单的使用
 */
@Slf4j
public class Demo1 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new T1("信号量-" + i).start();
        }
    }

    //创建了许可数量为2的信号量，每个线程获取1个许可，同时允许两个线程获取许可
    //其他线程需要等待已获取许可的线程释放许可之后才能运行
    static Semaphore semaphore = new Semaphore(2);

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                //未获取到许可的线程会阻塞在 acquire()方法上，直到获取到许可才能继续。
                semaphore.acquire();
                log.info("[{}],[{}] ,获取许可!", System.currentTimeMillis(), this.getName());
                TimeUnit.SECONDS.sleep(3);
                log.info("[{}],[{}] ,运行结束,当前可用许可数量:[{}]",
                        System.currentTimeMillis(),
                        this.getName(),
                        semaphore.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                log.info("[{}],[{}] ,释放许可!", System.currentTimeMillis(), this.getName());
            }


        }
    }
}