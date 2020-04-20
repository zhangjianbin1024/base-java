package com.dongnaoedu.tooluse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/07
 * 创建时间: 20:59
 * <p>
 * Exchanger（交换者）是一个用于线程间协作的工具类
 */
public class ExchangeCase9 {

    static final Exchanger<List<String>> exgr = new Exchanger<>();

    public static void main(String[] args) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    List<String> list = new ArrayList<>();

                    list.add(Thread.currentThread().getId() + " insert A1");
                    list.add(Thread.currentThread().getId() + " insert A2");

                    //交换数据
                    list = exgr.exchange(list);

                    for (String item : list) {
                        System.out.println(Thread.currentThread().getId() + ":" + item);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    List<String> list = new ArrayList<>();
                    list.add(Thread.currentThread().getId() + " insert B1");
                    list.add(Thread.currentThread().getId() + " insert B2");
                    list.add(Thread.currentThread().getId() + " insert B3");
                    System.out.println(Thread.currentThread().getId() + " will sleep");

                    Thread.sleep(1500);

                    //交换数据
                    list = exgr.exchange(list);

                    for (String item : list) {
                        System.out.println(Thread.currentThread().getId() + ":" + item);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
