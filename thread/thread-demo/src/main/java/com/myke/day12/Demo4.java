package com.myke.day12;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock获取锁的过程是可中断的
 * <p>
 * 发起获取锁请求到还未获取到锁这段时间内
 * <p>
 * 是可以被中断的，也就是说在等待锁的过程中，程序可以根据需要取消获取锁的请求
 *
 * @author: zh
 * @date: 2020/4/25/025 13:18
 */
@Slf4j
public class Demo4 {

    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1("t1", 1);
        T1 t2 = new T1("t2", 2);

        t1.start();
        t2.start();
        //线程t1在等待获取lock2，线程t2在等待获取lock1，都在相互等待获取对方持有的锁，最终产生了死锁

        //发送中断信息号，将不会产生死锁
        TimeUnit.SECONDS.sleep(5);
        //t2线程调用了 interrupt()方法，将线程的中断标志置为true
        t2.interrupt();
    }

    //非公平锁
    private static ReentrantLock lock1 = new ReentrantLock(false);
    private static ReentrantLock lock2 = new ReentrantLock(false);

    public static class T1 extends Thread {
        private int lockType;

        public T1(String name, int lockType) {
            super(name);
            this.lockType = lockType;
        }

        @Override
        public void run() {
            // lockInterruptibly()实例方法可以响应线程的中断方法，调用线程的interrupt()方法时，
            // lockInterruptibly()方法会触发 InterruptedException异常
            try {
                if (this.lockType == 1) {
                    lock1.lockInterruptibly();
                    TimeUnit.SECONDS.sleep(1);
                    lock2.lockInterruptibly();
                } else {
                    lock2.lockInterruptibly();
                    TimeUnit.SECONDS.sleep(2);
                    lock1.lockInterruptibly();
                }
            } catch (InterruptedException e) {
                //触发InterruptedException异常之后，线程的中断标志有会被清空，即置为false
                log.error("中断标志:[{}]", this.isInterrupted(), e);
            } finally {
                // isHeldByCurrentThread 判断当前线程是否持有ReentrantLock的锁
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }
            }


        }
    }
}
