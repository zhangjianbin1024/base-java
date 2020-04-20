package com.dongnaoedu.deadlock.bank;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/14
 * 创建时间: 20:14
 */
public class SimpleDeadLock3 {

    private static Object left = new Object();
    private static Object right = new Object();

    private static void leftToRight() throws InterruptedException {
        synchronized (left) {
            System.out.println(Thread.currentThread().getName() + " get left");
            Thread.sleep(100);
            synchronized (right) {
                System.out.println(Thread.currentThread().getName() + " get right");
            }
        }
    }

    /**
     * 不会产生死锁
     *
     * @throws InterruptedException
     */
    private static void rightToLeft() throws InterruptedException {
        synchronized (left) {
            System.out.println(Thread.currentThread().getName() + " get right-left");
            Thread.sleep(100);
            synchronized (right) {
                System.out.println(Thread.currentThread().getName() + " get left-right");
            }
        }
    }

    /**
     * 会产生死锁
     *
     * @throws InterruptedException
     */
    private static void rightToLeftDeadLock() throws InterruptedException {
        synchronized (right) {
            System.out.println(Thread.currentThread().getName() + " get right-left");
            Thread.sleep(100);
            synchronized (left) {
                System.out.println(Thread.currentThread().getName() + " get left-right");
            }
        }
    }

    private static class TestThread extends Thread {
        private String name;

        public TestThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                rightToLeft();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 简单问题死锁 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread.currentThread().setName("Main");
        TestThread testThread = new TestThread("testThread");
        // 先拿左锁，再拿右锁
        testThread.start();
        try {
            leftToRight();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
