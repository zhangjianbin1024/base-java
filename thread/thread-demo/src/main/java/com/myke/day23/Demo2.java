package com.myke.day23;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 统计网站页面访问量，假设网站有10个页面，
 * <p>
 * 现在模拟100个人并行访问每个页面10次，然后将每个页面访问量输出，应该每个页面都是1000次
 */
public class Demo2 {

    static AtomicIntegerArray pageRequest = new AtomicIntegerArray(new int[10]);

    /**
     * 模拟访问一次
     *
     * @param page 访问第几个页面
     *
     * @throws InterruptedException
     */
    public static void request(int page) throws InterruptedException {
        //模拟耗时5毫秒
        TimeUnit.MILLISECONDS.sleep(5);

        //pageCountIndex为pageCount数组的下标，表示页面对应数组中的位置
        int pageCountIndex = page - 1;

        // AtomicIntegerArray来操作数组中的每个元素，可以确保操作数据的原子性，
        // 每次访问会调用incrementAndGet，此方法需要传入数组的下标，然后对指定的元素做原子+1操作。
        pageRequest.incrementAndGet(pageCountIndex);
    }

    public static void main(String[] args) throws InterruptedException {
        long starTime = System.currentTimeMillis();

        int threadSize = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);

        for (int i = 0; i < threadSize; i++) {
            Thread thread = new Thread(() -> {
                try {
                    for (int page = 1; page <= 10; page++) {

                        //每一页访问10次
                        for (int j = 0; j < 10; j++) {
                            request(page);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }

        countDownLatch.await();

        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "，耗时：" + (endTime - starTime));

        for (int pageIndex = 0; pageIndex < 10; pageIndex++) {
            System.out.println("第" + (pageIndex + 1) + "个页面访问次数为" + pageRequest.get(pageIndex));
        }
    }
}