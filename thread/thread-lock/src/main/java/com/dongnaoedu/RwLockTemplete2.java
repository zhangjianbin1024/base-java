package com.dongnaoedu;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/30
 * 创建时间: 21:55
 * 读写锁 模版
 */
public class RwLockTemplete2 {

    static final Map<String, String> map = new HashMap<>();
    static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    static Lock r = reentrantReadWriteLock.readLock();
    static Lock w = reentrantReadWriteLock.writeLock();

    public void put() {
        w.lock();
        try {
            // do my work.....
        } finally {
            w.unlock();
        }
    }

    public void get() {
        r.lock();
        try {
            // do my work.....
        } finally {
            r.unlock();
        }
    }

}
