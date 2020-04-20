package com.dongnaoedu.tooluse;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/07
 * 创建时间: 20:36
 * <p>
 * CyclicBarrier可以用于多线程计算数据，最后合并计算结果的场景。
 */
public class CyclicBarrierSum8 {

    // 会先执行 SumThread 线程，再执行子线程 // 5 参数表示屏障拦截的线程数量
    static CyclicBarrier c = new CyclicBarrier(5, new SumThread());

    //子线程结果存放的缓存
    private static ConcurrentHashMap<String, Integer> resultMap =
            new ConcurrentHashMap<>();

    //所有子线程达到屏障后，会执行这个Runnable的任务
    private static class SumThread implements Runnable {// 汇总子线程的结果

        @Override
        public void run() {
            int result = 0;
            for (Map.Entry<String, Integer> workResult : resultMap.entrySet()) {
                result = result + workResult.getValue();
            }
            System.out.println("result = " + result);
            System.out.println("完全可以做与子线程，统计无关的事情.....");
        }
    }

    //工作线程，也就是子线程
    private static class WorkThread implements Runnable {

        private Random t = new Random();

        @Override
        public void run() {
            int r = t.nextInt(1000) + 1000;
            System.out.println(Thread.currentThread().getId() + ":r=" + r);
            // 存入数据
            resultMap.put(Thread.currentThread().getId() + "", r);
            try {
                Thread.sleep(1000 + r);
                c.await(); // 每个线程调用await方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 4; i++) {
            Thread thread = new Thread(new WorkThread());
            thread.start();
        }
    }
}
