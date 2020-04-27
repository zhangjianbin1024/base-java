package com.myke.day12;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock锁申请等待限时
 *
 * @author: zh
 * @date: 2020/4/27/027 22:05
 */
@Slf4j
public class Demo5 {

    public static void main(String[] args) {
        T1 t1 = new T1("t1");
        T1 t2 = new T1("t2");

        t1.start();
        t2.start();
    }

    private static ReentrantLock lock = new ReentrantLock();

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                log.info("[{}] ,[{}] ,开始获取锁!", System.currentTimeMillis(), this.getName());
                // tryLock()是立即响应的，中间不会有阻塞
                // lock.tryLock(3,TimeUnit.SECONDS //表示获取锁的超时时间是3秒，3秒后不管是否能获取锁，该方法都会有返回值
                // 返回true，表示获取锁成功，返回false表示获取失败
                if (lock.tryLock(3, TimeUnit.SECONDS)) {
                    log.info("[{}] ,[{}] ,获取到了锁!", System.currentTimeMillis(), this.getName());
                    // 获取到锁之后，休眠5秒,会导致另外一个线程获取锁失败
                    TimeUnit.SECONDS.sleep(5);
                } else {
                    log.info("[{}] ,[{}] ,未能获取到锁!", System.currentTimeMillis(), this.getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // isHeldByCurrentThread 判断当前线程是否持有ReentrantLock的锁
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }
}
