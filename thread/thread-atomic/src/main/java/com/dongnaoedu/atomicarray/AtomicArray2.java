package com.dongnaoedu.atomicarray;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/30
 * 创建时间: 20:49
 * <p>
 * 原子更新数组类:
 * AtomicIntegerArray，AtomicLongArray，AtomicReferenceArray
 * AtomicIntegerArray 类主要是提供原子的方式更新数组里的整型，
 */
public class AtomicArray2 {

    static int[] value = new int[]{1, 2};

    // 数组通过构造方法传入，类会将数组复制一份，原数组不会发生变化
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        // 将索引0 的值改为3
        ai.getAndSet(0, 3);
        System.out.println(ai.get(0));
        System.out.println(value[0]);
    }

}
