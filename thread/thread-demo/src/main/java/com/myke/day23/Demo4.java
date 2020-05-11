package com.myke.day23;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 充值消费--->发生ABA问题
 * <p>
 * 解决ABA 问题
 * <p>
 * 使用AtomicStampedRerence来修改一下充值的问题
 */
public class Demo4 {
    //账户原始余额
    static int accountMoney = 19;

    //用于对账户余额做原子操作
    static AtomicStampedReference<Integer> money = new AtomicStampedReference<>(accountMoney, 0);

    /**
     * 模拟2个线程同时更新后台数据库，为用户充值
     */
    static void recharge() {
        for (int i = 0; i < 2; i++) {
            //获得当前时间戳
            int stamp = money.getStamp();
            new Thread(() -> {

                // 对账户 充值 5 次
                for (int j = 0; j < 5; j++) {
                    //获得当前对象引用
                    Integer m = money.getReference();

                    if (m == accountMoney) {
                        //compareAndSet 比较设置，参数依次为：期望值、写入新值、期望时间戳、新时间戳
                        if (money.compareAndSet(m, m + 20, stamp, stamp + 1)) {
                            System.out.println("当前时间戳：" + money.getStamp() + ",当前余额：" + m + "，小于20，充值20元成功，余额：" + money.getReference() + "元");
                        } else {
                            System.out.println("线程名:" + Thread.currentThread().getName() + "充值失败");
                        }
                    }
                    //休眠100ms
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 模拟用户消费
     */
    static void consume() throws InterruptedException {
        // 对账户 消费 5 次
        for (int i = 0; i < 5; i++) {
            Integer m = money.getReference();
            int stamp = money.getStamp();
            if (m > 20) {
                if (money.compareAndSet(m, m - 20, stamp, stamp + 1)) {
                    System.out.println("当前时间戳：" + money.getStamp() + ",当前余额：" + m + "，大于20，成功消费20元，余额：" + money.getReference() + "元");
                }
            }
            //休眠50ms
            TimeUnit.MILLISECONDS.sleep(50);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        recharge(); //用户充值

        consume(); //用户消费
    }

}