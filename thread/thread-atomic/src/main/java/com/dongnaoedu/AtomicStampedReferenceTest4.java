package com.dongnaoedu;

import java.util.concurrent.atomic.AtomicStampedReference;


public class AtomicStampedReferenceTest4 {

    static AtomicStampedReference<Integer> atomicStampedReference =
            new AtomicStampedReference<>(0, 0);

    public static void main(String[] args) throws InterruptedException {
        //获取版本戳的值
        final int stamp = atomicStampedReference.getStamp();
        //获取值
        final Integer reference = atomicStampedReference.getReference();
        // 打印初始化的 值和版本戳
        System.out.println(reference + " =============>> " + stamp);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId() + ":" +
                        reference + "-" + stamp + "-" +
                        // compareAndSet 原子操作
                        atomicStampedReference.compareAndSet(reference, reference + 10, stamp, stamp + 1));
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //todo:这里应该重新获取版本戳，才能更新成功
                int stamp2 = atomicStampedReference.getStamp();

                Integer reference = atomicStampedReference.getReference();
                System.out.println(Thread.currentThread().getId() + ":"
                        + reference + "-" + stamp2 + "-" +
                        // 这里会检查版本戳，现在的版本本戳已经是 1 了，但拿到的版本戳却是 0 ，所以修改失败，返回false
                        atomicStampedReference.compareAndSet(reference, reference + 10, stamp2, stamp2 + 1));
            }
        });


        t1.start();
        t1.join(); // t1 线程执行完毕，t2 线程才能执行

        t2.start();
        t2.join();// t2 线程执行完毕，才能执行后面的语句

        // 打印更新后的值
        System.out.println(atomicStampedReference.getReference());

        // 打印版本戳的值
        System.out.println(atomicStampedReference.getStamp());

    }

}
