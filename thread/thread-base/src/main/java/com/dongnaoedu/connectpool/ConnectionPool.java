package com.dongnaoedu.connectpool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/15
 * 创建时间: 17:06
 * <p>
 * 数据库连接池
 * 从连接池中获取、使用和释放连接的过程，而客户端获取连接的过程被设定为等待超时的模式，
 * 也就是在1000毫秒内如果无法获取到可用连接，将会返回给客户端一个null。
 * 设定连接池的大小为10个，然后通过调节客户端的线程数来模拟无法获取连接的场景。
 * <p>
 * 连接池的定义。它通过构造函数初始化连接的最大上限，通过一个双向队列来维护连接，
 * 调用方需要先调用fetchConnection(long)方法来指定在多少毫秒内超时获取连接，当连接使用完成后，
 * 需要调用releaseConnection(Connection)方法将连接放回线程池
 */
public class ConnectionPool {

    // 存放连接的容器
    private LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.getConnectiong());
            }
        }
    }

    /*将连接放回线程池*/
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                // 添加后需要进行通知，这样其他消费者能够感知到链接池中已经归还了一个链接
                pool.addLast(connection);
                // 通知 这个对象上的线程，线程已经放回线程池中
                pool.notifyAll();
            }
        }
    }

    /**
     * 等待超时模式：
     * 指定在多少毫秒内超时获取连接，在指定时间内无法获取到连接，将会返回null
     */
    public Connection fetchConnection(long mills) throws InterruptedException {
        //对当前对象加锁
        synchronized (pool) {
            // 完全超时
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                // 超时的时间，也就是什么时候超时
                long future = System.currentTimeMillis() + mills;
                // 超时的时长
                long remaining = mills;

                while (pool.isEmpty() && remaining > 0) {
                    // 线程等待时间
                    pool.wait(remaining);
                    // 线程等待时间 失效后，重新计算线程持续等待的时间
                    // 也就是当前还需等待的时间
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }


    public Connection fetchConnection2(long mills) throws InterruptedException {
        //对当前对象加锁
        synchronized (pool) {
            if (mills <= 0) { //即使超时，继续等待
                return null;
            } else {
                // 超时的时间
                long future = System.currentTimeMillis() + mills;
                // 等待持续时间
                long remaining = mills;

                while (pool.isEmpty() && remaining > 0) {
                    // 线程等待时间，释放连接后，也会通知等待的线程
                    pool.wait(remaining);
                    // 线程等待时间 失效后，重新计算线程持续等待的时间
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
