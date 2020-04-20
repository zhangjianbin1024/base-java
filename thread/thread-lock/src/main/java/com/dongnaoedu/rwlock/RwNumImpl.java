package com.dongnaoedu.rwlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/30
 * 创建时间: 22:05
 * <p>
 * 读写锁ReentrantReadWriteLock 测试
 */
public class RwNumImpl implements IGoodsNum {

    private GoodsVo goods;

    //读写锁
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock r = lock.readLock();
    private final Lock w = lock.writeLock();

    public RwNumImpl(GoodsVo goods) {
        this.goods = goods;
    }


    /**
     * 读数据，当有多个线程时，可以同时进入 执行业务逻辑
     *
     * @return
     */
    @Override
    public GoodsVo getGoodsNumber() {
        r.lock();
        try {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this.goods;
        } finally {
            // todo: 释放锁一定要放到 finally 中
            r.unlock();
        }
    }

    /**
     * 写数据 只有一个线程进入 执行业务逻辑
     *
     * @param changeNumber
     */
    @Override
    public void setGoodsNumber(int changeNumber) {
        w.lock();
        try {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.goods.setGoodsVoNumber(changeNumber);
        } finally {
            w.unlock();
        }
    }
}
