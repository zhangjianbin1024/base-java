package com.myke.day11;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 通过一个变量控制线程中断
 *
 * @author: zh
 * @date: 2020/4/25/025 11:00
 */
@Slf4j
public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1("thread-1");
        t1.start();
        TimeUnit.SECONDS.sleep(3);

        setExit();
    }

    //volatile控制了变量在多线程中的可见性
    public volatile static boolean exit = false;

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                if (!exit) {
                    log.info("[{}] 退出", this.getName());
                    break;
                }
            }
        }
    }

    public static void setExit() {
        exit = true;
    }
}
