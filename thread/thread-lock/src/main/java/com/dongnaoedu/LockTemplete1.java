package com.dongnaoedu;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/30
 * 创建时间: 21:36
 * <p>
 * 使用 lock 锁 模版
 */
public class LockTemplete1 {

    public static void main(String[] args) {
        // 可重入锁
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            // do my work.....
        } finally {
            //释放锁
            lock.unlock();
        }
    }

}
