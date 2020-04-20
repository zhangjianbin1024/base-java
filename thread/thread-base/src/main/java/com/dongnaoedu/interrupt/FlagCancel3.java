package com.dongnaoedu.interrupt;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/26
 * 创建时间: 20:32
 * <p>
 * 使用自定义的标志位中断线程（不安全）
 */
public class FlagCancel3 {

    private static class TestRunable implements Runnable {

        private volatile boolean on = true;
        private long i = 0;

        //
        //@Override
        //public void run() {
        //    while (on) {
        //        System.out.println(i++);
        //    }
        //    System.out.println("TestRunable is runing :" + i);
        //}


        /**
         * 取消线程标志位
         */
        public void cancel() {
            System.out.println("Ready stop thread......");
            on = false;
        }

        @Override
        public void run() {
            while (on) { // 此 on 标志位是不起做用的
                System.out.println(i++);
                //sleep, wait等等方法时 当前线程会阻塞，是不会理会我们自己设置的取消标志位的，
                // 但是这些阻塞方法都会检查线程的中断标志位。
                // 单独使用一个取消线程标志位.是不安全的
                //阻塞方法，on不起作用 如wait,sleep,阻塞队列中的方法(put,take)
                try {
                    synchronized (this) {
                        wait(); // 线程会阻塞在这里
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("TestRunable is runing :" + i);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        TestRunable testRunable = new TestRunable();
        Thread t = new Thread(testRunable);
        t.start();
        Thread.sleep(10);
        // 给线程发送终止信号
        testRunable.cancel();
    }
}
