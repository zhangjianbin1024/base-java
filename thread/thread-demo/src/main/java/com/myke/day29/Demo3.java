package com.myke.day29;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 使用令牌桶算法来进行限流
 * <p>
 * 令牌桶算法的原理是系统以恒定的速率产生令牌，然后把令牌放到令牌桶中，
 * 令牌桶有一个容量，当令牌桶满了的时候，再向其中放令牌，
 * 那么多余的令牌会被丢弃；当想要处理一个请求的时候，
 * 需要从令牌桶中取出一个令牌，如果此时令牌桶中没有令牌，那么则拒绝该请求
 * <p>
 * 从原理上看，令牌桶算法和漏桶算法是相反的，一个“进水”，一个是“漏水”。
 * 这种算法可以应对突发程度的请求，因此比漏桶算法好。
 */
public class Demo3 {

    //输出结果：第一段每次输出相隔200毫秒，第二段每次输出相隔100毫秒，可以非常精准的控制系统的QPS
    public static void main(String[] args) throws InterruptedException {
        //设置QPS为5
        RateLimiter rateLimiter = RateLimiter.create(5);

        for (int i = 0; i < 10; i++) {
            rateLimiter.acquire();
            System.out.println(System.currentTimeMillis());
        }
        System.out.println("----------");

        //可以随时调整速率，我们将qps调整为10
        rateLimiter.setRate(10);

        for (int i = 0; i < 10; i++) {
            rateLimiter.acquire();
            System.out.println(System.currentTimeMillis());
        }

    }
}