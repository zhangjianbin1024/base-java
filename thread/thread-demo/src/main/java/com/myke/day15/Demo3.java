package com.myke.day15;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore 在规定的时间内希望获取许可
 */
@Slf4j
public class Demo3 {
    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1("t1");
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        T1 t2 = new T1("t2");
        t2.start();

        TimeUnit.SECONDS.sleep(1);

        T1 t3 = new T1("t3");
        t3.start();


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
                //尝试在1秒内获取许可，获取成功返回true，否则返回false
                acquireSucess = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (acquireSucess) {
                    ///获取成功执行业务代码

                    log.info("[{}],[{}] ,获取许可成功!,当前可用许可数量:[{}]",
                            System.currentTimeMillis(),
                            this.getName(),
                            semaphore.availablePermits());

                    TimeUnit.SECONDS.sleep(5);
                } else {
                    log.info("[{}],[{}] ,获取许可失败,当前可用许可数量:[{}]",
                            System.currentTimeMillis(),
                            this.getName(),
                            semaphore.availablePermits());
                }
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