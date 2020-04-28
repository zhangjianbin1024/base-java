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
public class Demo2 {

    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1("t1");
        t1.start();
        TimeUnit.SECONDS.sleep(5);
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
                // t1线程等待2秒之后，自动返回继续执行
                // await返回 false表示 超时之后自动返回
                // await返回 true表示 await方法超时之前被其他线程唤醒了。
                boolean await = condition.await(2, TimeUnit.SECONDS);
                log.info("[{}],[{}] 准备释放锁! await [{}]", System.currentTimeMillis(), this.getName(), await);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            log.info("[{}],[{}] 释放锁成功!", System.currentTimeMillis(), this.getName());
        }
    }
}
