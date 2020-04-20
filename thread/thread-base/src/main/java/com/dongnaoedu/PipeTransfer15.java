package com.dongnaoedu;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/28
 * 创建时间: 21:40
 * <p>
 * 管道输入输出流
 * <p>
 * 管道输入输出流用于线程中间的数据传递，传输媒介的内存
 */
public class PipeTransfer15 {

    /**
     * 打印接受的数据
     */
    private static class Print implements Runnable {
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.println((char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 管道输出
        PipedWriter out = new PipedWriter();
        // 管道输入
        PipedReader in = new PipedReader();

        //将两个管道进行连接
        out.connect(in);

        // 启动一个线程,将 管道输入的内容传递给 打印接收数据的线程
        Thread t1 = new Thread(new Print(in), "PrintThread");
        t1.start();

        int receive = 0;
        try {
            // 监听键盘的输入，read() 是一个阻塞的方法
            while ((receive = System.in.read()) != -1) {
                out.write(receive);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

}
