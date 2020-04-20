package com.dongnaoedu.aqs;

import com.dongnaoedu.SleepUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/20
 * 创建时间: 17:42
 */
public class TestMyLock6 {

    public void test() {

        // 使用 jdk 中提供的可重入锁，在递归中不会出现死锁
        // 可重入锁:也就是说在递归中，第一次线程拿到了锁，如果第二次线程还是该上一次的线程，
        // 再次调用该方法时，也可以拿到锁，此时的 state 的状态值会进行累加
        final Lock lock = new ReentrantLock();

        // 使用 自已的独占锁 同一时刻只有一个线程获取到锁
        //final Lock lock = new SingleLock();

        //使用自定义的共享锁，同一时刻可以有两个线程获取到锁，日志中每次打印两个线程名
        //final Lock lock = new SharedLock();

        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        //打印当前线程的名子
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                    SleepUtils.second(2);
                }
            }
        }

        // 启动10个子线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            //w.setDaemon(true);
            // 虽然有10个线程，但同一时刻只有一个线程能拿到锁，
            /**
             * 当使用自定义独占锁时，其它线程怎么处理呢？ 放入阻塞对队中
             *
             * 通过调用同步器的acquire(int arg)方法可以获取同步状态，
             * 其主要逻辑是：首先调用自定义同步器实现的tryAcquire(int arg)方法，
             * 该方法保证线程安全的获取同步状态，如果同步状态获取失败，
             * 则构造同步节点（独占式Node.EXCLUSIVE，同一时刻只能有一个线程成功获取同步状态）
             * 并通过addWaiter(Node node)方法将该节点加入到同步队列的尾部，
             * 最后调用acquireQueued(Node node,int arg)方法，使得该节点以“死循环”的方式
             * 获取同步状态。如果获取不到则阻塞节点中的线程，而被阻塞线程的唤醒主要依靠前驱节点的出队
             * 或阻塞线程被中断来实现
             */
            w.start();
        }

        // 主线程每隔1秒换行
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TestMyLock6 testMyLock = new TestMyLock6();
        testMyLock.test();
    }
}
