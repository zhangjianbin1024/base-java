package com.dongnaoedu.interrupt;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/26
 * 创建时间: 21:00
 * 如何覆盖线程的interrupt() 方法
 */
public class OverrideInterrupt5 extends Thread {
    private final Socket socket;
    private final InputStream in;

    public OverrideInterrupt5(Socket socket, InputStream in) {
        this.socket = socket;
        this.in = in;
    }

    private void t() {
        //in.read()  //线程会阻塞，不会理会中断信号
    }


    /**
     * 覆盖线程的interrupt() 方法
     */
    @Override
    public void interrupt() {
        try {
            //关闭底层的套接字
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            //.....
        } finally {
            //同时中断线程
            super.interrupt();
        }

    }
}
