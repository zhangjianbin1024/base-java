package com.dongnaoedu.interrupt;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/26
 * 创建时间: 21:02
 * 调用阻塞方法时，如何中断线程
 */
public class BlockInterrupt6 {

    private static volatile boolean on = true;

    private static class WhenBlock implements Runnable {

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                try {
                    //抛出中断异常的阻塞方法，抛出异常后，中断标志位改成false
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();//所以这里要，重新设置一下
                    //do my work
                }
                //清理工作结束线程
            }
        }

        public void cancel() {
            on = false;
            Thread.currentThread().interrupt();
        }

    }

    private static Object o = new Object();

    /*while循环中包含try/catch块*/
    private static class WhileTryWhenBlock extends Thread {
        private volatile boolean on = true;
        private long i = 0;

        @Override
        public void run() {
            System.out.println("当前执行线程id：" + Thread.currentThread().getId());
            // 检查线程是否中断
            while (on && !Thread.currentThread().isInterrupted()) {
                System.out.println("i=" + i++);
                try {
                    //抛出中断异常的阻塞方法，抛出异常后，中断标志位会改成false
                    //可以理解为这些方法会隐含调用Thread.interrputed()方法
                    synchronized (o) {
                        // wait 线程会阻塞，但这里会自动的不断的检查当前线程标志位的状态，检查到中断信号，会抛出异常，并将线程标志位设为false
                        o.wait();
                    }

                } catch (InterruptedException e) {
                    System.out.println("当前执行线程的中断标志位："
                            + Thread.currentThread().getId()
                            + ":" + Thread.currentThread().isInterrupted());

                    //todo:需要手动的重新设置一下，如果注释掉此处，即使外部发送了中断信号，但 上面 o.wait 也不会退出
                    Thread.currentThread().interrupt();
                    System.out.println("被中断的线程_" + getId()
                            + ":" + isInterrupted());
                    //do my work
                }
                //清理工作，准备结束线程
            }
        }

        public void cancel() {
            //on = false;

            // 使用这个方法，会中断 WhileTryWhenBlock 启的线程
            interrupt();
            System.out.println("本方法所在线程实例：" + getId());

            // 打印的是主线的id,也就是 main 线程，因为中断信号是在外部调用的，也就是main 线程中调用的
            System.out.println("执行本方法的线程id：" + Thread.currentThread().getId() + " 执行本方法的线程名字： " + Thread.currentThread().getName());

            //使用这个方法，线程中断在外部调用时，会将被用调用的线程中断，也里是对 main 线程中断，但 WhileTryWhenBlock 线程不会中止
            //Thread.currentThread().interrupt();
        }
    }

    /*try/catch块中包含while循环*/
    private static class TryWhileWhenBlock extends Thread {
        private volatile boolean on = true;
        private long i = 0;

        @Override
        public void run() {
            try {
                while (on) {
                    System.out.println(i++);
                    //抛出中断异常的阻塞方法，抛出异常后，中断标志位改成false
                    synchronized (o) {
                        o.wait();
                    }
                }
            } catch (InterruptedException e) {
                //todo:没有手动的重新设置一下线程中断信号标志位

                System.out.println("当前执行线程的中断标志位："
                        + Thread.currentThread().getId()
                        + ":" + Thread.currentThread().isInterrupted());
            } finally {
                //清理工作结束线程
            }
        }

        public void cancel() {
            on = false;
            interrupt();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        //WhileTryWhenBlock whileTryWhenBlock = new WhileTryWhenBlock();
        //whileTryWhenBlock.start();
        //
        //Thread.sleep(100);
        //// 给线程发送中断信号，调用实例对象的 cancel，在　jvm 中会将该方法加载到主线程中执行，也就是main线程
        //whileTryWhenBlock.cancel();


        TryWhileWhenBlock tryWhileWhenBlock = new TryWhileWhenBlock();
        tryWhileWhenBlock.start();

        Thread.sleep(100);
        // 给线程发送线程中断信号，这个线程抛出异常，线程run 方法执行完毕，线程结束，注意和上面线程的区别
        tryWhileWhenBlock.cancel();
    }
}