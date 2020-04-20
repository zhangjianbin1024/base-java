package com.dongnaoedu.interrupt;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/26
 * 创建时间: 20:42
 * 安全的中断线程
 */
public class SafeInterrupt4 implements Runnable {
    /**
     * 线程中断标志位
     */
    private volatile boolean on = true;
    private long i = 0;

    @Override
    public void run() {

        // isInterrupted 线程检查自己的中断标志位,判断线程是否已被发送过中断请求
        while (on && !Thread.currentThread().isInterrupted()) {
            i++;
            try {
                System.out.println("线程休眠：===>>" + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();//重新设置一下
                e.printStackTrace();
            }
        }
        System.out.println("TestRunable is runing :" + i);
    }

    public void cancel() {
        on = false;
        // interrupt 中断线程，本质是将线程的中断标志位设为 true
        Thread.currentThread().interrupt();
        System.out.println("设置线程执行结束标志位");
    }

    public static void main(String[] args) throws InterruptedException {
        SafeInterrupt4 safeInterrupt4 = new SafeInterrupt4();

        Thread thread = new Thread(safeInterrupt4);

        thread.start();

        for (int i = 0; i < 100; i++) {
            if (i == 80) {
                safeInterrupt4.cancel();
                System.out.println("结束线程=========>>" + i);
            }
        }
    }
}
