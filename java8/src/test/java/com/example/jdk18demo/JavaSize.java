package com.example.jdk18demo;


import com.carrotsearch.sizeof.RamUsageEstimator;

import java.util.ArrayList;

public class JavaSize {
    ArrayList<Integer> javaSizeResult = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        JavaSize javaSize = new JavaSize();

        //计算指定对象及其引用树上的所有对象的综合大小，返回可读的结果，如：2KB
        System.out.println("humanSizeOf:" + RamUsageEstimator.humanSizeOf(javaSize));
        //计算指定对象本身在堆空间的大小，单位字节
        System.out.println("shallowSizeOf:" + RamUsageEstimator.shallowSizeOf(javaSize));
        //计算指定对象及其引用树上的所有对象的综合大小，单位字节
        System.out.println("sizeOf:" + RamUsageEstimator.sizeOf(javaSize));
        System.out.println("==============================");
        javaSize.objectSize();
        Thread.sleep(1000000000);

    }

    private void objectSize() {

        for (int i = 0; i < 1000; i++) {
            javaSizeResult.add(i);
        }
        //计算指定对象及其引用树上的所有对象的综合大小，返回可读的结果，如：2KB
        System.out.println("humanSizeOf:" + RamUsageEstimator.humanSizeOf(javaSizeResult));
        //计算指定对象本身在堆空间的大小，单位字节
        System.out.println("shallowSizeOf:" + RamUsageEstimator.shallowSizeOf(javaSizeResult));
        //计算指定对象及其引用树上的所有对象的综合大小，单位字节
        System.out.println("sizeOf:" + RamUsageEstimator.sizeOf(javaSizeResult));

        System.out.println("sizeOf:" + RamUsageEstimator.sizeOf(1));
    }


}