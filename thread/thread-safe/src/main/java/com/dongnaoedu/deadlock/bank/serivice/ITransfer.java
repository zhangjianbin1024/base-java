package com.dongnaoedu.deadlock.bank.serivice;


import com.dongnaoedu.deadlock.bank.Account;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/08/30
 * 创建时间: 15:35
 */
public interface ITransfer {

    // 银行转账操作
    void transfer(Account from, Account to, int amount) throws InterruptedException;
}
