package com.myke.day14;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport 测试
 *
 * @author: zh
 * @date: 2020/4/28/028 22:30
 */
@Slf4j
public class Demo1 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.info("[{}],[{}] start", System.currentTimeMillis(), Thread.currentThread().getName());
            //让当前线程t1等待
            //park传入的这个参数可以让我们在线程堆栈信息中方便排查问题
            // LockSupport.park()
            LockSupport.park(new Demo1());
            log.info("[{}],[{}] 被唤醒", System.currentTimeMillis(), Thread.currentThread().getName());
        });

        t1.setName("t1");
        t1.start();

        TimeUnit.SECONDS.sleep(5);
        //将t1线程唤醒
        LockSupport.unpark(t1);
        log.info("[{}] LockSupport.unpark();执行完毕", System.currentTimeMillis());
    }
}
