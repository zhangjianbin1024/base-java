package com.dongnaoedu.safeclass;

import com.dongnaoedu.deadlock.bank.Account;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/10
 * 创建时间: 22:22
 * <p>
 * 无状态类:（无成员变量）
 */
public class Stateless1 {

    private final int x = 10;
    private final String y = "dddfd";

    public int getX() {
        return x;
    }

//    public Account getY() {
//        //return y;
//    }

    public void check(int a, Account b) {
        int i = 0;
        //Account account = new Account();

        //do my work

    }
}
