package com.myke.day17;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 重建规则
 * <p>
 * 员工1等待5秒超时之后，开吃了，打破了规则，先前等待中的以及后面到达的都不按规则来了，
 * 都拿起筷子开吃。过了一会，导游重新告知大家，要按规则来，然后重建了规则，大家都按规则来了。
 */
@Slf4j
public class Demo5 {

    /**
     * 第一次规则被打乱了，过了一会导游重建了规则（cyclicBarrier.reset();），
     * <p>
     * 接着又重来来了一次模拟等待吃饭的操作，正常了。
     *
     * @param args
     *
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            T t = new T("员工" + i, i);
            t.start();
        }

        //等待10秒之后，重置，重建规则
        TimeUnit.SECONDS.sleep(10);
        cyclicBarrier.reset();
        guizhe = true;
        System.out.println("---------------大家太皮了，请大家按规则来------------------");
        //再来一次
        for (int i = 1; i <= 10; i++) {
            T t = new T("员工" + i, i);
            t.start();
        }
    }

    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

    //规则是否已重建
    public static boolean guizhe = false;


    public static class T extends Thread {
        int sleep;

        public T(String name, int sleep) {
            super(name);
            this.sleep = sleep;
        }

        @Override
        public void run() {
            long startTime = 0L;
            try {
                TimeUnit.SECONDS.sleep(sleep);
                startTime = System.currentTimeMillis();
                log.info("[{}] 到了！", this.getName());
                if (!guizhe) {
                    if (this.getName().equals("员工1")) {
                        cyclicBarrier.await(5, TimeUnit.SECONDS);
                    } else {
                        cyclicBarrier.await();
                    }
                } else {
                    // 调用await()的时候，当前线程将会被阻塞，
                    // 每调用一次await()方法都将使阻塞的线程数+1，
                    // 只有阻塞的线程数达到设定值时屏障才会打开，允许阻塞的所有线程继续执行
                    cyclicBarrier.await();

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }


            long endStime = System.currentTimeMillis();
            log.info("[{}],sleep 等待了 [{}] (ms),开始吃饭了", this.getName(), endStime - startTime);
        }
    }

}