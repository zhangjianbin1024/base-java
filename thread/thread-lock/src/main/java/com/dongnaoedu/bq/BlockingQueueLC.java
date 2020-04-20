package com.dongnaoedu.bq;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/30
 * 创建时间: 22:23
 * <p>
 * lock 通知机制，有界阻塞队列
 */
public class BlockingQueueLC<T> {
    private List queue = new LinkedList<>();
    private final int limit;

    Lock lock = new ReentrantLock();

    // 不为空锁
    private Condition needNotEmpty = lock.newCondition();
    // 为空锁
    private Condition needNotFull = lock.newCondition();


    public BlockingQueueLC(int limit) {
        this.limit = limit;
    }

    // 入队
    public void enqueue(T item) throws InterruptedException {
        lock.lock();//锁
        try {
            while (this.queue.size() == this.limit) {
                needNotFull.await();// 当队列满时，则入队线程等待
            }
            this.queue.add(item);
            needNotEmpty.signal(); // 唤醒出队等待的线程,告诉出队的线程，队列不为空了，可以拿数据了
        } finally {
            lock.unlock();// 释放锁
        }
    }

    // 出队
    public T dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (this.queue.size() == 0) {
                // 对队为空时，线程等待，但是当入队线程添加数据时，会唤醒该锁，继续望下执行
                needNotEmpty.await();
            }
            // 当等待线程被唤醒，开始执行到这里时，通知入队线程可以添加数据了，因为下面会取出数据，则队列不会是满的了
            needNotFull.signal();
            return (T) this.queue.remove(0); // 数据从队列中取出
        } finally {
            lock.unlock();
        }
    }
}
