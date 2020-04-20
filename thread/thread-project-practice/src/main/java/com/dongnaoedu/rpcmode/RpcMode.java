package com.dongnaoedu.rpcmode;

import com.dongnaoedu.aim.Consts;
import com.dongnaoedu.aim.MakeSrcDoc;
import com.dongnaoedu.aim.ProblemBank;
import com.dongnaoedu.service.DocService;
import com.dongnaoedu.vo.PendingDocVo;

import java.util.List;
import java.util.concurrent.*;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/12
 * 创建时间: 20:32
 * <p>
 * 版本二
 * <p>
 * 服务的拆分，rpc服务
 */
public class RpcMode {

    //生成文档的线程池
    private static ExecutorService docMakeService
            = Executors.newFixedThreadPool(Consts.THREAD_COUNT_BASE * 2);
    //上传文档的线程池
    private static ExecutorService docUploadService
            = Executors.newFixedThreadPool(Consts.THREAD_COUNT_BASE * 2);

    //todo:先生成的，先上传
    private static CompletionService docCompletionService
            = new ExecutorCompletionService(docMakeService);

    private static CompletionService uploadCompletionService
            = new ExecutorCompletionService(docUploadService);


    //生成文档
    private static class MakeDocTask implements Callable<String> {
        private PendingDocVo pendingDocVo;

        public MakeDocTask(PendingDocVo pendingDocVo) {
            this.pendingDocVo = pendingDocVo;
        }

        @Override
        public String call() throws Exception {
            long start = System.currentTimeMillis();
            //异步并行处理文档中的题目
            String localName = DocService.makeAsyn(pendingDocVo);
            System.out.println("文档" + localName + "生成耗时："
                    + (System.currentTimeMillis() - start) + "ms");
            return localName;
        }
    }


    //上传文档
    private static class UploadDocTask implements Callable<String> {
        private String localName;

        public UploadDocTask(String localName) {
            this.localName = localName;
        }

        @Override
        public String call() throws Exception {
            long start = System.currentTimeMillis();
            String remoteUrl = DocService.upLoadDoc(localName);
            System.out.println("已上传至[" + remoteUrl + "]耗时："
                    + (System.currentTimeMillis() - start) + "ms");
            return remoteUrl;
        }
    }

    public static void main(String[] args)
            throws InterruptedException, ExecutionException {
        System.out.println("题库开始初始化...........");
        ProblemBank.initBank();
        System.out.println("题库初始化完成。");

        //形成待处理文档
        List<PendingDocVo> docList = MakeSrcDoc.makeDoc(60);

        long startTotal = System.currentTimeMillis();

        for (PendingDocVo doc : docList) {
            //提交 doc 生成文档任务
            docCompletionService.submit(new MakeDocTask(doc));
        }

        for (PendingDocVo doc : docList) {
            // 获取当前文档的本地地址
            Future<String> futureLocalName = docCompletionService.take();
            // 提交 上传文档任务
            uploadCompletionService.submit(new UploadDocTask(futureLocalName.get()));
        }

        for (PendingDocVo doc : docList) {
            //把上传后的网络存储地址拿到，打印文件
            uploadCompletionService.take().get();
        }
        System.out.println("共耗时：" + (System.currentTimeMillis() - startTotal) + "ms");
    }
}
