package com.myke.day23;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 有一家蛋糕店，为了挽留客户，决定为贵宾卡客户一次性赠送20元，
 * 刺激客户充值和消费，但条件是，每一位客户只能被赠送一次，
 * <p>
 * 现在我们用AtomicReference来实现这个功能
 */
public class Demo3 {

    //账户原始余额
    static int accountMoney = 19;

    //用于对账户余额做原子操作
    static AtomicReference<Integer> money = new AtomicReference<>(accountMoney);

    /**
     * 模拟2个线程同时更新后台数据库，为用户充值
     */
    static void recharge() {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                // 对账户 充值 5 次
                for (int j = 0; j < 5; j++) {
                    Integer m = money.get();
                    if (m == accountMoney) {
                        if (money.compareAndSet(m, m + 20)) {
                            System.out.println("当前余额：" + m + "，小于20，充值20元成功，余额：" + money.get() + "元");
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
            Integer m = money.get();
            if (m > 20) {
                if (money.compareAndSet(m, m - 20)) {
                    System.out.println("当前余额：" + m + "，大于20，成功消费20元，余额：" + money.get() + "元");
                }
            }
            //休眠50ms
            TimeUnit.MILLISECONDS.sleep(50);
        }
    }

    /**
     * 从输出中可以看到
     * <p>
     * 这个账户被先后反复多次充值。其原因是账户余额被反复修改，修改后的值和原有的数值19一样，
     * 使得CAS操作无法正确判断当前数据是否被修改过（是否被加过20）
     *
     * @param args
     *
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        recharge(); //用户充值

        consume(); //用户消费
    }

}