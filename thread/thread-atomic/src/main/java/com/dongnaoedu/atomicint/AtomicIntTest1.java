package com.dongnaoedu.atomicint;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/30
 * 创建时间: 15:37
 * <p>
 * 原子更新基本类型类:
 * AtomicBoolean，AtomicInteger，AtomicLong，AtomicReference
 */
public class AtomicIntTest1 {
    // 初始化默认值为 1
    static AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        //getAndIncrement 原子递增，但是返回的是自增以前的值
        System.out.println(ai.getAndIncrement());
        // incrementAndGet 原子递增，但是返回的是自增以后的值
        ai.incrementAndGet();
        System.out.println(ai.get());
    }
}
