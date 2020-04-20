package com.dongnaoedu.forkjoin.BaJie;


import com.dongnaoedu.forkjoin.MakePanTaoArray;
import com.dongnaoedu.forkjoin.SunWuKong.Impl.WuKongPickImpl;
import com.dongnaoedu.forkjoin.SunWuKong.Impl.WuKongProcessImpl;
import com.dongnaoedu.forkjoin.service.IPickTaoZi;
import com.dongnaoedu.forkjoin.service.IProcessTaoZi;
import com.dongnaoedu.forkjoin.vo.PanTao;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/10/12
 * 创建时间: 16:45
 * 八戒 摘桃
 * <p>
 * forkjoin 框架异步处理任务，无返回结果
 */
public class ForkJoinBaJieAsyn5 {

    private static class XiaoBaJie extends RecursiveAction {

        private final static int THRESHOLD = 100;
        private PanTao[] src;
        private int fromIndex;
        private int toIndex;
        private IPickTaoZi pickTaoZi;

        public XiaoBaJie(PanTao[] src, int fromIndex, int toIndex,
                         IPickTaoZi pickTaoZi) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.pickTaoZi = pickTaoZi;
        }

        @Override
        protected void compute() {
            if (toIndex - fromIndex < THRESHOLD) {
                System.out.println(" from index = " + fromIndex + " toIndex=" + toIndex);
                int count = 0;
                for (int i = fromIndex; i <= toIndex; i++) {
                    if (pickTaoZi.pick(src, i)) {
                        count++;
                    }
                }
            } else {
                //fromIndex....mid.....toIndex
                int mid = (fromIndex + toIndex) / 2;
                XiaoBaJie left = new XiaoBaJie(src, fromIndex, mid, pickTaoZi);
                XiaoBaJie right = new XiaoBaJie(src, mid + 1, toIndex, pickTaoZi);
                invokeAll(left, right);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        ForkJoinPool pool = new ForkJoinPool();
        PanTao[] src = MakePanTaoArray.makeArray();
        IProcessTaoZi processTaoZi = new WuKongProcessImpl();
        IPickTaoZi pickTaoZi = new WuKongPickImpl(processTaoZi);

        long start = System.currentTimeMillis();

        XiaoBaJie xiaoBaJie = new XiaoBaJie(src, 0,
                src.length - 1, pickTaoZi);

        // 异步方法，主线程可以继续执行
        pool.execute(xiaoBaJie);
        System.out.println("BaJie is picking.....");

        Thread.sleep(2);
        System.out.println("Please waiting.....");

        while (!xiaoBaJie.isDone()) {//任务没有完成时，打印线程池相关信息
            showLog(pool);
            TimeUnit.MILLISECONDS.sleep(100);
        }

        // 关闭线程池
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
        showLog(pool);

        // 获取子任务结果，子任务执行完毕时，主线程继续往下执行
        xiaoBaJie.join();
        System.out.println(" spend time:" + (System.currentTimeMillis() - start) + "ms");


    }

    //监控Fork/Join池相关方法
    private static void showLog(ForkJoinPool pool) {
        System.out.printf("**********************\n");

        System.out.printf("线程池的worker线程们的数量:%d\n",
                pool.getPoolSize());
        System.out.printf("当前执行任务的线程的数量:%d\n",
                pool.getActiveThreadCount());
        System.out.printf("没有被阻塞的正在工作的线程:%d\n",
                pool.getRunningThreadCount());
        System.out.printf("已经提交给池还没有开始执行的任务数:%d\n",
                pool.getQueuedSubmissionCount());
        System.out.printf("已经提交给池已经开始执行的任务数:%d\n",
                pool.getQueuedTaskCount());
        System.out.printf("线程偷取任务数:%d\n",
                pool.getStealCount());
        System.out.printf("池是否已经终止 :%s\n",
                pool.isTerminated());
        System.out.printf("**********************\n");
    }

}
