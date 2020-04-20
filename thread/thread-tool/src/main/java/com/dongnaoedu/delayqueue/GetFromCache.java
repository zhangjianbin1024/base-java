package com.dongnaoedu.delayqueue;

import java.util.concurrent.DelayQueue;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/05
 * 创建时间: 21:44
 * <p>
 * 从阻塞队列中获取元素
 */
public class GetFromCache implements Runnable {

    // 阻塞队列
    private DelayQueue<CacheBean<User>> queue;

    public GetFromCache(DelayQueue<CacheBean<User>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // take ，移除元素，当没有元素时，一直阻塞
                CacheBean<User> item = queue.take();
                System.out.println("GetFromCache" + item.getId() + ":" + item.getName() +
                        "data:" + ((User) item.getData()).getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
