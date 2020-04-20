package com.dongnaoedu.delayqueue;

import java.util.concurrent.DelayQueue;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/05
 * 创建时间: 21:51
 * 使用阻塞对队，实现一个定时缓存
 * <p>
 * 实现 定时缓存，队列中的元素多长时间失效，类似redis 设置key的失效时间
 */
public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        //阻塞队列
        DelayQueue<CacheBean<User>> queue = new DelayQueue<CacheBean<User>>();

        // 向对队列中添加数据
        new Thread(new PutInCache(queue)).start();

        // 从队列中取出数据
        new Thread(new GetFromCache(queue)).start();

        for (int i = 1; i < 20; i++) {
            Thread.sleep(500);
            System.out.println(i * 500);
        }
    }
}
