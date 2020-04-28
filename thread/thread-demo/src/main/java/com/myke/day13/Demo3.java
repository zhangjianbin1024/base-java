package com.myke.day13;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列测试
 *
 * @author: zh
 * @date: 2020/4/28/028 21:54
 */
@Slf4j
public class Demo3 {

    /**
     * 创建了一个阻塞队列，大小为 2，队列满的时候，会被阻塞，等待其他线程去消费，
     * 队列中的元素被消费之后，会唤醒生产者，生产数据进入队列.
     * <p>
     * 将队列大小置为1，可以实现同步阻塞队列，生产1个元素之后，生产者会被阻塞，
     * 待消费者消费队列中的元素之后，生产者才能继续工作。
     *
     * @param args
     */
    public static void main(String[] args) {
        BlockingQueuDemo<Integer> queuDemo = new BlockingQueuDemo<>(2);

        for (int i = 0; i < 10; i++) {
            int data = i;
            new Thread(() -> {
                try {
                    queuDemo.enquene(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            int data = i;
            new Thread(() -> {
                try {
                    queuDemo.dequeue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


    public static class BlockingQueuDemo<E> {
        //阻塞队列最大容量
        int size;

        private ReentrantLock lock = new ReentrantLock();
        //队列
        private LinkedList<E> list = new LinkedList<>();

        // 队列满时的等待条件
        Condition notFull = lock.newCondition();

        // 队列空时的等待条件
        Condition notEmpty = lock.newCondition();

        public BlockingQueuDemo(int size) {
            this.size = size;
        }

        public void enquene(E e) throws InterruptedException {
            lock.lock();
            try {
                while (list.size() == size) {
                    //队列已满,在notFull条件上等待
                    notFull.await();
                }
                //入队，加入到链表末尾
                list.add(e);
                log.info("入队:[{}]", e);

                //通知在notEmpty条件上等待的线程
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public E dequeue() throws InterruptedException {
            E e;
            lock.lock();
            try {
                while (list.size() == 0) {
                    // 队列为空,在notEmpty条件上等待
                    notEmpty.await();
                }
                //出队:移除链表首元素
                e = list.removeFirst();
                log.info("出队:[{}]", e);
                //通知在notFull条件上等待的线程
                notFull.signal();
                return e;
            } finally {
                lock.unlock();
            }
        }
    }
}
