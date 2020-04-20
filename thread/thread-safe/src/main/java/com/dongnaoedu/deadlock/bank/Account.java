package com.dongnaoedu.deadlock.bank;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/08/30
 * 创建时间: 15:32
 */
public class Account {
    private long number;
    private final String name;
    private int money;

    // 俱有尝试获取锁的能力
    private final Lock lock = new ReentrantLock();

    public Lock getLock() {
        return lock;
    }

    public Account(String name, int amount) {
        this.name = name;
        this.money = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    public void addMoney(int amount) {
        money = money + amount;
    }

    public void flyMoney(int amount) {
        money = money - amount;
    }
}
