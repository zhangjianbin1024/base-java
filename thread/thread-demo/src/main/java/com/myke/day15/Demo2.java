package com.myke.day15;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore 正确的释放锁的方式
 * <p>
 * 程序中增加了一个变量 acquireSuccess用来标记获取许可是否成功，
 * 在finally中根据这个变量是否为true，来确定是否释放许可。
 */
@Slf4j
public class Demo2 {
    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1("t1");
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        T1 t2 = new T1("t2");
        t2.start();

        TimeUnit.SECONDS.sleep(1);

        T1 t3 = new T1("t3");
        t3.start();

        TimeUnit.SECONDS.sleep(1);

        //给t2和t3发送中断信号
        t2.interrupt();
        t3.interrupt();
    }

    //创建了许可数量为1的信号量，每个线程获取1个许可，同时允许两个线程获取许可
    //其他线程需要等待已获取许可的线程释放许可之后才能运行
    static Semaphore semaphore = new Semaphore(1);

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            //获取许可是否成功
            boolean acquireSucess = false;

            try {
                //未获取到许可的线程会阻塞在 acquire()方法上，直到获取到许可才能继续。
                semaphore.acquire();
                acquireSucess = true;

                log.info("[{}],[{}] ,获取许可!", System.currentTimeMillis(), this.getName());

                TimeUnit.SECONDS.sleep(5);

                log.info("[{}],[{}] ,运行结束,当前可用许可数量:[{}]",
                        System.currentTimeMillis(),
                        this.getName(),
                        semaphore.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (acquireSucess) {
                    semaphore.release();
                    log.info("[{}],[{}] ,释放许可!", System.currentTimeMillis(), this.getName());
                }

            }


        }
    }
}