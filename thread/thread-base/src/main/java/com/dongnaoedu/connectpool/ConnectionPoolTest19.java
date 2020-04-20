package com.dongnaoedu.connectpool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/15
 * 创建时间: 17:06
 * <p>
 * 等待超时模式
 * 调用一个方法时等待一段时间（一般来说是给定一个时间段），
 * 如果该方法能够在给定的时间段之内得到结果，那么将结果立刻返回，反之，超时返回默认结果或者抛出异常信息。
 */
public class ConnectionPoolTest19 {
    // 初始化，数据库连接池
    static ConnectionPool pool = new ConnectionPool(10);

    // 保证所有ConnectionRunner能够同时开始
    static CountDownLatch start = new CountDownLatch(1);

    // main 线程将会等待所有ConnectionRunner结束后才能继续执行
    static CountDownLatch end;

    public static void main(String[] args) throws Exception {
        // 线程数量，可以线程数量进行观察
        int threadCount = 50;
        end = new CountDownLatch(threadCount);

        int count = 10;//每个线程循环取10次

        AtomicInteger got = new AtomicInteger();//获取到数据库连接的次数
        AtomicInteger notGot = new AtomicInteger();//没有获取到数据库连接的次数

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnetionRunner(count, got, notGot),
                    "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown(); // 通过所有的线程可以执行了,相当于线程的法令枪

        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection:  " + got);
        System.out.println("not got connection " + notGot);
    }

    static class ConnetionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnetionRunner(int count, AtomicInteger got,
                               AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                // 所有线程都在这等待
                start.await();
            } catch (Exception ex) {

            }
            while (count > 0) {
                try {
                    // 从线程池中获取连接，如果1000ms内无法获取到，将会返回null
                    // 分别统计连接获取的数量got和未获取到的数量notGot
                    int mills = 1000;
                    Connection connection = pool.fetchConnection(mills);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            // 释放数据库连接
                            pool.releaseConnection(connection);
                            got.incrementAndGet();//统计获取数据库连接的次数
                        }
                    } else {
                        if (connection == null) {
                            System.out.println(Thread.currentThread().getId() +
                                    " 在指定的时间" + mills + "ms未能获得连接！！！");
                        }
                        notGot.incrementAndGet(); // 统计没有获取数据库连接的次数
                    }
                } catch (Exception ex) {
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
