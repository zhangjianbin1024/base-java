package com.example.jdk18demo.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;

public class XiaoMing {
    public static void main(String[] args) {
        //生动态代理 class 文件位置
        String path = XiaoMing.class.getResource("").getPath();
        System.out.println("生动态代理 class 文件位置" + path);
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, path);


        SellProxyFactory sellProxyFactory = new SellProxyFactory();
        // 获取一个代理实例
        SellPerfumeFactory proxyInstance =
                (SellPerfumeFactory) sellProxyFactory.getProxyInstance(new SellPerfumeFactory());
        // 创建代理类
        proxyInstance.sellPerfume(1999.99);


    }
}