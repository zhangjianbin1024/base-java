package com.dongnaoedu.atomicint;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/30
 * 创建时间: 15:33
 */
public class AtomicIntSafeCount {
    private AtomicInteger atomicI = new AtomicInteger(0);

    private void safeCount() {
        for (;;) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

}
