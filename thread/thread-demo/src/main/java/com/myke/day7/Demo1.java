package com.myke.day7;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * volatile 测试
 *
 * @author: zh
 * @date: 2020/4/22/022 22:07
 */
@Slf4j
public class Demo1 {
    /**
     * <code>volatile</code>
     * <p>
     * 1. 线程中读取的时候，每次读取都会去主内存中读取共享变量最新的值，然后将其复制到工作内存
     * <p>
     * 2. 线程中修改了工作内存中变量的副本，修改之后会立即刷新到主内存
     */
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new T1("thread-1").start();

        TimeUnit.SECONDS.sleep(1);

        flag = false;

        log.info("main 线程执行完了");

    }

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            log.info("线程 [{}] in", this.getName());
            while (flag) {

            }
            log.info("线程 [{}] out", this.getName());
        }
    }
}
