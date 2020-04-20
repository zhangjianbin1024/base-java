package com.dongnaoedu.bq;

import java.util.LinkedList;
import java.util.List;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/28
 * 创建时间: 21:07
 * 有界阻塞队列
 */
public class BlockingQueueWN<T> {

    private List queue = new LinkedList<>();
    //队列容量的限定
    private final int limit;

    public BlockingQueueWN(int limit) {
        this.limit = limit;
    }

    //入队
    public synchronized void enqueue(T item) throws InterruptedException {

        // 对队满了，则等待
        while (this.queue.size() == this.limit) {
            wait();
        }
        //将数据入队，可以肯定有出队的线程正在等待
        if (this.queue.size() == 0) {
            // 这里为了通知出队的线程，来获取数据，当该入队方法执行完时，
            // 出队线程才能进入通步方法块中取数据
            notifyAll();
        }
        this.queue.add(item);
    }

    //出队
    public synchronized T dequeue() throws InterruptedException {
        // 当队列为空时，则等待
        while (this.queue.size() == 0) {
            wait();
        }
        if (this.queue.size() == this.limit) {
            // 因为在下面的逻辑中数据准备出队了，队列则不是满的，所以通知入队线程可以添加数据
            notifyAll();
        }
        return (T) this.queue.remove(0);
    }
}
