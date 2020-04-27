package com.myke.day12;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 创建公平锁
 *
 * @author: zh
 * @date: 2020/4/25/025 13:00
 */
@Slf4j
public class Demo3 {
    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1("thread-1");
        T1 t2 = new T1("thread-2");
        T1 t3 = new T1("thread-3");

        t1.start();
        t2.start();
        t3.start();

        //等待3个线程执行完毕之后，打印num的值
        t1.join();
        t2.join();
        t3.join();


    }

    //创建公平锁
    private static ReentrantLock fairLock = new ReentrantLock(true);

    /**
     * 锁
     */
    public static void getLock() {
        //获取锁
        fairLock.lock();
        try {
            //输出的结果，锁时按照先后顺序获得的
            log.info("[{}] 获取锁", Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            fairLock.unlock();
        }
    }

    public static void m1() {
        for (int i = 0; i < 10; i++) {
            getLock();
        }
    }

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            Demo3.m1();
        }
    }
}
