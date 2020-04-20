package com.dongnaoedu.deadlock.bank.serivice;

import com.dongnaoedu.deadlock.bank.Account;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/14
 * 创建时间: 20:31
 */
public class SafeTransfer implements ITransfer {


    private static Object tieLock = new Object();

    @Override
    public void transfer(Account from, Account to, int amount)
            throws InterruptedException {

        //天生具有顺序
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        if (fromHash < toHash) {
            synchronized (from) {// 先锁小的，再锁大的
                System.out.println(Thread.currentThread().getName() + " get " + from.getName());
                Thread.sleep(100);
                synchronized (to) {
                    System.out.println(Thread.currentThread().getName()
                            + " get " + to.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                    System.out.println(from);
                    System.out.println(to);
                }
            }
        } else if (toHash < fromHash) {
            synchronized (to) { // 先锁大的，再锁小的
                System.out.println(Thread.currentThread().getName() + " get " + to.getName());
                Thread.sleep(100);
                synchronized (from) {
                    System.out.println(Thread.currentThread().getName()
                            + " get " + from.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                    System.out.println(from);
                    System.out.println(to);
                }
            }
        } else {
            synchronized (tieLock) { // hash值 相等的情况下
                synchronized (to) {
                    System.out.println(Thread.currentThread().getName() + " get " + to.getName());
                    Thread.sleep(100);
                    synchronized (from) {
                        System.out.println(Thread.currentThread().getName()
                                + " get " + from.getName());
                        from.flyMoney(amount);
                        to.addMoney(amount);
                    }
                }
            }
        }
    }
}
