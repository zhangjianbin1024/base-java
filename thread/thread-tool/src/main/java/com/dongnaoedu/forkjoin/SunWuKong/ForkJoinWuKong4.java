package com.dongnaoedu.forkjoin.SunWuKong;


import com.dongnaoedu.forkjoin.MakePanTaoArray;
import com.dongnaoedu.forkjoin.SunWuKong.Impl.WuKongPickImpl;
import com.dongnaoedu.forkjoin.SunWuKong.Impl.WuKongProcessImpl;
import com.dongnaoedu.forkjoin.service.IPickTaoZi;
import com.dongnaoedu.forkjoin.service.IProcessTaoZi;
import com.dongnaoedu.forkjoin.vo.PanTao;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/10/12
 * 创建时间: 16:45
 * <p>
 * forkjoin 框架同步处理任务，有返回结果
 * <p>
 * 悟空一边偷桃子，一边统计个数
 */
public class ForkJoinWuKong4 {

    // RecursiveTask 用于有返回结果的任务处理
    // 悟空 生成了好多小悟空
    private static class XiaoWuKong extends RecursiveTask<Integer> {

        //阈值，数组到底多小的时候，就进行具体的业务操作
        // 这里指，桃子的数组多小的时候，就不再拆分了任务了，就开始处理这个数组了
        private final static int THRESHOLD = 100;


        private PanTao[] src;
        private int fromIndex;
        private int toIndex;
        private IPickTaoZi pickTaoZi;

        public XiaoWuKong(PanTao[] src, int fromIndex, int toIndex, IPickTaoZi pickTaoZi) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.pickTaoZi = pickTaoZi;
        }

        /**
         * 对桃子进行统计
         * <p>
         * 这个方法，就完成了大任务拆分成小任务，由小悟空来完成
         *
         * @return
         */
        @Override
        protected Integer compute() {
            if (toIndex - fromIndex < THRESHOLD) {// 桃子数组大小 小于定义的 阈值 时，就开始业务处理了，也不是处理这个数组了
                System.out.println(" from index = " + fromIndex + " toIndex=" + toIndex);
                int count = 0;
                for (int i = fromIndex; i < toIndex; i++) {
                    // 摘桃子
                    if (pickTaoZi.pick(src, i)) {
                        count++; //统计摘桃子个数
                    }
                }
                return count;
            } else {
                // 大于 定义的 阈值 时，也就是说这个数组太大了，就将任务继续拆分
                //fromIndex....mid......toIndex

                int mid = (fromIndex + toIndex) / 2; // 求出中间下标

                // 这里生成小悟空，继续处理这个数组，如果数组大小还大于定义的阈值，则继续拆分成小悟空来处理这个数组
                XiaoWuKong left = new XiaoWuKong(src, fromIndex, mid, pickTaoZi); // 从开始下标到中间下标

                XiaoWuKong right = new XiaoWuKong(src, mid, toIndex, pickTaoZi); // 从中间下标到数组末尾下标

                // 将任务提交给 ForkJoin 框架
                invokeAll(left, right);

                // 所有小任务的结果 就是 大任务的结果
                // join 是一个阻塞方法，会一直等到子任务将任务做完了，才返回
                return left.join() + right.join();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        // ForkJoinTask 需要通过 ForkJoinPool 来执行
        ForkJoinPool pool = new ForkJoinPool(); // 线程大小不能控制，只能通过阀值来控制

        // 蟠桃，桃子生成器
        PanTao[] src = MakePanTaoArray.makeArray();

        //摘下桃子如何处理的接口 实现
        IProcessTaoZi processTaoZi = new WuKongProcessImpl();
        //摘桃子的接口 实现
        IPickTaoZi pickTaoZi = new WuKongPickImpl(processTaoZi);

        long start = System.currentTimeMillis();

        // 桃子数组传递给悟空
        XiaoWuKong xiaoWuKong = new XiaoWuKong(src, 0,
                src.length - 1, pickTaoZi);


        // 将任务提交给 ForkJoinPool，由小悟空去执行任务
        // invoke 是一个同步方法，子任务执行不完，是不会往下走的，则主线程也阻塞在这里
        pool.invoke(xiaoWuKong);


        System.out.println("The count is " + xiaoWuKong.join()
                + " spend time:" + (System.currentTimeMillis() - start) + "ms");
    }
}
