package com.myke.day10;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo1 {


    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1("thread-1");
        T1 t2 = new T1("thread-2");
        T1 t3 = new T1("thread-3");

        t1.start();
        t2.start();
        t3.start();

        //等待3个线程执行完毕之后，打印num的值
        t1.join();
        t2.join();
        t3.join();

        //从结果上得知，num 计算是线程不安全的
        log.info("num:[{}]", num);

    }

    //共享资源(临界资源)
    static int num = 0;

    public static void m1() {
        for (int i = 0; i < 100000; i++) {
            num++;
        }
    }

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            Demo1.m1();
        }
    }
}