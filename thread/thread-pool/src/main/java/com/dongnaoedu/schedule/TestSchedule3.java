package com.dongnaoedu.schedule;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/10
 * 创建时间: 20:53
 */
public class TestSchedule3 {
    public static void main(String[] args) {

        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

        /**
         * 每5秒打印一次
         *
         * 每隔一段时间打印系统时间，互不影响的创建并执行一个在给定初始延迟后首次启用的定期操作，
         * 后续操作具有给定的周期；
         * 也就是将在 initialDelay 后开始执行，周期为 period。
         */
        exec.scheduleAtFixedRate(new ScheduleTask(ScheduleTask.OperType.None),
                1000, 5000, TimeUnit.MILLISECONDS); //单位毫秒

        // 开始执行后就触发异常,next周期将不会运行
        exec.scheduleAtFixedRate(new ScheduleTask(ScheduleTask.OperType.OnlyThrowException),
                1000, 5000, TimeUnit.MILLISECONDS);

        // 虽然抛出了运行异常,当被拦截了,next周期继续运行
        exec.scheduleAtFixedRate(new ScheduleTask(ScheduleTask.OperType.CatheException),
                1000, 5000, TimeUnit.MILLISECONDS);

        /**
         * 创建并执行一个在给定初始延迟后首次启用的定期操作，
         * 随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。
         */
        exec.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("scheduleWithFixedDelay:begin"
                        + ScheduleTask.formater.format(new Date()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("scheduleWithFixedDelay:end"
                        + ScheduleTask.formater.format(new Date()));
            }
        }, 1000, 5000, TimeUnit.MILLISECONDS);

        /**
         * 创建并执行在给定延迟后启用的一次性操作。
         */
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("schedule running....." + ScheduleTask.formater.format(new Date()));
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }


}
