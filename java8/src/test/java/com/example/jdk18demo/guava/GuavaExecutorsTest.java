package com.example.jdk18demo.guava;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.List;

public class GuavaExecutorsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaExecutors.class);

    @Test
    public void newCachedExecutorService() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ListeningExecutorService listeningExecutorService = GuavaExecutors.newCachedExecutorService("GuavaExecutorsTest-");
        List<ListenableFuture<?>> listenableFutures = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            ListenableFuture<?> submit = listeningExecutorService.submit(() -> {
                LOGGER.info("==>1");
            });
            listenableFutures.add(submit);

            //线程执行情况
            Futures.addCallback(submit, new FutureCallback<Object>() {
                @Override
                public void onSuccess(Object result) {
                    LOGGER.info("线程:[{}] success", Thread.currentThread().getName());
                }

                @Override
                public void onFailure(Throwable t) {
                    LOGGER.info("线程:[{}] fail", Thread.currentThread().getName());
                }
            });
        }

        try {
            //listListenableFuture 用于判断多线程什么时候全部执行完，全都执行完后，
            ListenableFuture<List<Object>> listListenableFuture = Futures.successfulAsList(listenableFutures);
            List<Object> objects = listListenableFuture.get();
            int size = objects.size();
            stopWatch.stop();
            LOGGER.info("GuavaExecutorsTest 共计线程数:[{}] 已执行完毕,耗时：[{}]", size, stopWatch.getLastTaskTimeMillis());
            listeningExecutorService.shutdown();
        } catch (Exception e) {
            LOGGER.error("GuavaExecutorsTest 执行失败", e);
        }
        LOGGER.info("test end");
    }
}