package com.myke.day12;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 非公平锁
 *
 * @author: zh
 * @date: 2020/4/25/025 12:36
 */
@Slf4j
public class Demo2 {
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

        log.info("num:[{}]", num);

    }

    //创建锁，默认构造方法创建的是非公平锁
    private static ReentrantLock lock = new ReentrantLock();

    //共享资源(临界资源)
    static int num = 0;

    /**
     * ReentrantLock 可重入锁
     * <p>
     * lock()方法和unlock()方法需要成对出现，
     * 锁了几次，也要释放几次，否则后面的线程无法获取锁了
     */
    public static void caculateNum2() {
        //获取锁
        lock.lock();
        lock.lock();

        try {
            num++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
            lock.unlock();
        }
    }

    /**
     * 锁
     */
    public static void caculateNum() {
        //获取锁
        lock.lock();
        try {
            num++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }

    public static void m1() {
        for (int i = 0; i < 100000; i++) {
            //caculateNum();
            caculateNum2();
        }
    }

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            Demo2.m1();
        }
    }
}
