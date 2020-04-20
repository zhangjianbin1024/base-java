package com.dongnaoedu.rwlock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/09/20
 * 创建时间: 15:16
 */
public class Test3 {

    static final int threadRatio = 10;
    static final int threadBaseCount = 3;
    // 线程发令枪
    static CountDownLatch countDownLatch = new CountDownLatch(1);

    //模拟实际的数据库读操作
    private static class ReadThread implements Runnable {

        private IGoodsNum goodsNum;

        public ReadThread(IGoodsNum goodsNum) {
            this.goodsNum = goodsNum;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long start = System.currentTimeMillis();
            // 每个线程对读操作执行 100　次
            for (int i = 0; i < 100; i++) {
                goodsNum.getGoodsNumber();
            }
            long duration = System.currentTimeMillis() - start;
            System.out.println(Thread.currentThread().getName() + "读取库存数据耗时：" + duration + "ms");

        }
    }


    //模拟实际的数据库写操作
    private static class WriteThread implements Runnable {

        private IGoodsNum goodsNum;

        public WriteThread(IGoodsNum goodsNum) {
            this.goodsNum = goodsNum;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long start = System.currentTimeMillis();
            Random r = new Random();
            //每个线程对写操作，执行了 10 次
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                goodsNum.setGoodsNumber(r.nextInt(10));
            }
            long duration = System.currentTimeMillis() - start;
            System.out.println(Thread.currentThread().getName() + "写库存数据耗时：" + duration + "ms");

        }
    }

    public static void main(String[] args) throws InterruptedException {
        GoodsVo goodsVo =
                new GoodsVo("goods001", 100000, 10000);


        // synchronized 锁情况
        //IGoodsNum goodsNum = new NumSyn(goodsVo);

        // ReentrantReadWriteLock 锁情况 ，读写锁性能是synchronized 的8倍多
        IGoodsNum goodsNum = new RwNumImpl(goodsVo);


        for (int i = 0; i < threadBaseCount * threadRatio; i++) {
            Thread readT = new Thread(new ReadThread(goodsNum));
            readT.start();
        }


        for (int i = 0; i < threadBaseCount; i++) {
            Thread writeT = new Thread(new WriteThread(goodsNum));
            writeT.start();
        }
        // 读写线程同时执行，模拟并发的情况
        countDownLatch.countDown();

    }

}
