package com.myke.day29;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用漏桶算法来进行限流
 * <p>
 * 漏桶算法思路很简单，水（请求）先进入到漏桶里，漏桶以一定的速度出水，
 * 当水流入速度过大会直接溢出，可以看出漏桶算法能强行限制数据的传输速率
 */
public class Demo2 {

    public static class BucketLimit {
        static AtomicInteger threadNum = new AtomicInteger(1);

        //容量
        private int capcity;
        //流速
        private int flowRate;
        //流速时间单位
        private TimeUnit flowRateUnit;

        private BlockingQueue<Node> queue;

        //漏桶流出的任务时间间隔（纳秒）
        private long flowRateNanosTime;

        public BucketLimit(int capcity, int flowRate, TimeUnit flowRateUnit) {
            this.capcity = capcity;
            this.flowRate = flowRate;
            this.flowRateUnit = flowRateUnit;
            this.bucketThreadWork();
        }

        //漏桶线程
        public void bucketThreadWork() {
            this.queue = new ArrayBlockingQueue<Node>(capcity);
            //漏桶流出的任务时间间隔（纳秒）
            this.flowRateNanosTime = flowRateUnit.toNanos(1) / flowRate;

            Thread thread = new Thread(this::bucketWork);
            thread.setName("漏桶线程-" + threadNum.getAndIncrement());
            thread.start();
        }

        //漏桶线程开始工作
        public void bucketWork() {
            while (true) {
                // 从队列中获取数据
                Node node = this.queue.poll();
                if (Objects.nonNull(node)) {
                    //唤醒任务线程
                    LockSupport.unpark(node.thread);
                }

                // park 当前线程就会阻塞 休眠flowRateNanosTime
                LockSupport.parkNanos(this.flowRateNanosTime);
            }
        }

        //返回一个漏桶
        public static BucketLimit build(int capcity, int flowRate, TimeUnit flowRateUnit) {
            if (capcity < 0 || flowRate < 0) {
                throw new IllegalArgumentException("capcity、flowRate必须大于0！");
            }
            return new BucketLimit(capcity, flowRate, flowRateUnit);
        }

        //当前线程加入漏桶，返回false，表示漏桶已满；true：表示被漏桶限流成功，可以继续处理任务
        public boolean acquire() {
            Thread thread = Thread.currentThread();
            Node node = new Node(thread);
            //向队列中添加数据，添加成功，返回true,添加失败返回false，添加成功说明可以处理当前请求
            if (this.queue.offer(node)) {
                //阻塞当前线程
                LockSupport.park();
                return true;
            }
            return false;
        }

        //漏桶中存放的元素
        class Node {
            private Thread thread;

            public Node(Thread thread) {
                this.thread = thread;
            }
        }
    }

    public static void main(String[] args) {
        //创建了一个容量为10，流水为60/分钟的漏桶。
        BucketLimit bucketLimit = BucketLimit.build(10, 60, TimeUnit.MINUTES);
        for (int i = 0; i < 15; i++) {
            new Thread(() -> {
                boolean acquire = bucketLimit.acquire();
                System.out.println(System.currentTimeMillis() + " ;目前桶的大小:" + bucketLimit.queue.size() + " " + acquire);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}