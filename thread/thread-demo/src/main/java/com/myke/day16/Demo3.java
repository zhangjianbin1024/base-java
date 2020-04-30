package com.myke.day16;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 2个CountDown结合使用
 * <p>
 * 有3个人参加跑步比赛，需要先等指令员发指令枪后才能开跑，
 * 所有人都跑完之后，指令员喊一声，大家跑完了。
 */
@Slf4j
public class Demo3 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatchCd = new CountDownLatch(1);
        CountDownLatch countDown = new CountDownLatch(3);

        long startTime = System.currentTimeMillis();
        T t1 = new T("小张", 2, countDownLatchCd, countDown);
        t1.start();

        T t2 = new T("小李", 5, countDownLatchCd, countDown);
        t2.start();

        T t3 = new T("小王", 10, countDownLatchCd, countDown);
        t3.start();

        // 模拟发枪准备操作耗时5秒，然后调用 countDownLatchCd.countDown();
        // 模拟发枪操作，此方法被调用以后，阻塞在 countDownLatchCd.await();的3个线程会向下执行
        TimeUnit.SECONDS.sleep(5);
        log.info("[{}],枪响了，大家开始跑", System.currentTimeMillis());
        countDownLatchCd.countDown();

        // 主线程调用 countDown.await();之后进行等待，
        // 每个人跑完之后，调用 countDown.countDown();
        // 通知一下 countDown 让计数器减1，最后3个人都跑完了，
        // 主线程从 countDown.await();返回继续向下执行。
        countDown.await();
        long endTime = System.currentTimeMillis();
        log.info("[{}], 所有人跑完了，主线程耗时:[{}]", endTime, endTime - startTime);

    }

    public static class T extends Thread {
        //跑步耗时
        int runCostSeconds;

        CountDownLatch countDownLatchCd;
        CountDownLatch countDown;

        public T(String name, int runCostSeconds, CountDownLatch countDownLatchCd, CountDownLatch countDown) {
            super(name);
            this.runCostSeconds = runCostSeconds;
            this.countDownLatchCd = countDownLatchCd;
            this.countDown = countDown;
        }

        @Override
        public void run() {
            //等待指令员枪响
            try {
                // t1、t2、t3启动之后，都阻塞在 commanderCd.await();
                countDownLatchCd.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long startTime = System.currentTimeMillis();
            log.info("[{}],[{}],开始跑!", startTime, this.getName());

            try {
                //模拟耗时操作，休眠runCostSeconds秒
                TimeUnit.SECONDS.sleep(runCostSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDown.countDown();
            }

            long endTime = System.currentTimeMillis();
            log.info("[{}],[{}] 跑步结束,耗时:[{}]", endTime, this.getName(), endTime - startTime);
        }
    }

}