package com.dongnaoedu;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/30
 * 创建时间: 21:36
 * <p>
 * Condition 模版
 * <p>
 * Condition接口和Lock配合来实现等待通知机制
 */
public class ConditionTemplete4 {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    /**
     * lock 线程等待
     *
     * @throws InterruptedException
     */
    public void waitc() throws InterruptedException {
        lock.lock();
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
    }

    /**
     * lock 线程通知
     *
     * @throws InterruptedException
     */
    public void waitnotify() throws InterruptedException {
        lock.lock();
        try {
            condition.signal(); // 因为 lock 锁和condition紧密联系在一起的，知道哪个锁，只会唤醒 condition.await(); 等待的线程
            //condition.signalAll();尽量少使用
        } finally {
            lock.unlock();
        }
    }


}
