package com.dongnaoedu.deadlock.bank.serivice;

import com.dongnaoedu.deadlock.bank.Account;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/14
 * 创建时间: 20:25
 */
public class NormalTransfer implements ITransfer {
    /**
     * 无法控制调用方，转入的顺序
     *
     * @param from
     * @param to
     * @param amount
     * @throws InterruptedException
     */
    @Override
    public void transfer(Account from, Account to, int amount)
            throws InterruptedException {
        synchronized (from) { // 先锁转入账户
            System.out.println(Thread.currentThread().getName() + " get " + from.getName());
            Thread.sleep(100);
            synchronized (to) {// 再锁转出账户
                System.out.println(Thread.currentThread().getName()
                        + " get " + to.getName());
                from.flyMoney(amount);
                to.addMoney(amount);
            }
        }
    }
}
