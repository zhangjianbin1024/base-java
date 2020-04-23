package com.myke.day9;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户线程和守护线程测试
 */
@Slf4j
public class Demo1 {
    public static void main(String[] args) {
        T1 t1 = new T1("子线程");

        //将t1线程设置为守护线程,main方法所在的主线程执行完毕之后，程序就退出了。
        //t1.setDaemon(true);

        t1.start();

        log.info("主线程结束");
    }


    public static class T1 extends Thread {

        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            log.info("[{}] 开始执行，[{}]", this.getName(), this.isDaemon() ? "我是守护线程" : "我是用户线程");
            while (true) {

            }
        }
    }
}