package com.dongnaoedu;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/28
 * 创建时间: 21:53
 * <p>
 * 线程A，执行了thread.join(),线程A等待thread线程终止了以后，A在join后面的语句才会继续执行
 * <p>
 * join 相当于线程排队，坏处：join 进来的线程，可能在这个线程里还 join 了好多线程进来，这样，当前线程就的一直等待
 */
public class JoinTest16 {

    static class CutInLine implements Runnable {

        private Thread thread;

        public CutInLine(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                //在被插队的线程里，调用一下插队线程的join方法
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " will work");
        }
    }

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();

        for (int i = 0; i < 10; i++) {
            Thread thread =
                    new Thread(new CutInLine(previous), String.valueOf(i));
            System.out.println(previous.getId() + " cut in the thread:" + thread.getName());
            thread.start();

            previous = thread;
        }

    }

}
