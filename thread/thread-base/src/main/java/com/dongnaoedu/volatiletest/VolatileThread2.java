package com.dongnaoedu.volatiletest;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/28
 * 创建时间: 20:29
 * 测试Volatile型变量的操作原子性
 */
public class VolatileThread2 implements Runnable {

    @Override
    public void run() {
        int a = 0;

        a = a + 1;
        System.out.println(Thread.currentThread().getName() + "----" + a);
        //try {
        //    Thread.sleep(0);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        a = a + 1;
        System.out.println(Thread.currentThread().getName() + "----" + a);

    }


    public static void main(String[] args) {
        VolatileThread2 volatileThread = new VolatileThread2();

        //执行四个线和
        Thread t1 = new Thread(volatileThread);
        Thread t2 = new Thread(volatileThread);
        Thread t3 = new Thread(volatileThread);
        Thread t4 = new Thread(volatileThread);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

}
