package com.myke.day13;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 测试
 *
 * @author: zh
 * @date: 2020/4/28/028 21:30
 */
@Slf4j
public class Demo1 {

    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1("t1");
        t1.start();

        TimeUnit.SECONDS.sleep(5);

        T2 t2 = new T2("t2");
        t2.start();
    }


    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            log.info("[{}],[{}] 准备获取锁!", System.currentTimeMillis(), this.getName());
            lock.lock();
            try {
                log.info("[{}],[{}] 获取锁成功!", System.currentTimeMillis(), this.getName());
                //Condition.await()方法和Object.wait()方法类似,
                //在Condition.await()方法被调用时，当前线程会释放这个锁，并且当前线程会进行等待（处于阻塞状态）
                condition.await();
                log.info("[{}],[{}] 准备释放锁!", System.currentTimeMillis(), this.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            log.info("[{}],[{}] 释放锁成功!", System.currentTimeMillis(), this.getName());
        }
    }

    public static class T2 extends Thread {
        public T2(String name) {
            super(name);
        }

        @Override
        public void run() {
            log.info("[{}],[{}] 准备获取锁!", System.currentTimeMillis(), this.getName());
            lock.lock();

            try {
                log.info("[{}],[{}] 获取锁成功!", System.currentTimeMillis(), this.getName());

                // 在signal()方法被调用后，系统会从Condition对象的等待队列中唤醒一个线程，
                // 一旦线程被唤醒，被唤醒的线程会尝试重新获取锁，一旦获取成功，就可以继续执行了
                condition.signal();
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("[{}],[{}] 准备释放锁!", System.currentTimeMillis(), this.getName());
            } finally {
                lock.unlock();
            }
            log.info("[{}],[{}] 释放锁成功!", System.currentTimeMillis(), this.getName());
        }
    }
}
