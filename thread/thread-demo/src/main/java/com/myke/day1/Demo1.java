package com.myke.day1;

public class Demo1 {


    public static void main(String[] args) {
        Obj1 obj1 = new Obj1();
        Obj2 obj2 = new Obj2();

        Thread thread_1 = new Thread(new SynAddRunable(obj1, obj2, 1, 2, true));
        thread_1.setName("thread-1");
        thread_1.start();

        Thread thread_2 = new Thread(new SynAddRunable(obj1, obj2, 1, 2, false));
        thread_2.setName("thread-2");
        thread_2.start();

    }

    /**
     * 线程死锁等待演示
     */
    public static class SynAddRunable implements Runnable {
        Obj1 obj1;
        Obj2 obj2;
        int a, b;
        boolean flag;

        public SynAddRunable(Obj1 obj1, Obj2 obj2, int a, int b, boolean flag) {
            this.obj1 = obj1;
            this.obj2 = obj2;
            this.a = a;
            this.b = b;
            this.flag = flag;
        }

        @Override
        public void run() {
            try {
                if (flag) {
                    // synchronized 阻塞的线程
                    //执行后续代码前，得到临界区的锁，如果得不到，线程就会被挂起等待，直到占有了所需资源为止
                    synchronized (obj1) {
                        Thread.sleep(100);
                        synchronized (obj2) {
                            System.out.println(a + b);
                        }
                    }
                } else {
                    synchronized (obj2) {
                        Thread.sleep(100);
                        synchronized (obj1) {
                            System.out.println(a + b);
                        }
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private static class Obj1 {
    }

    private static class Obj2 {
    }
}