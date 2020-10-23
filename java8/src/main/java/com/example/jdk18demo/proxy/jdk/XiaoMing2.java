package com.example.jdk18demo.proxy.jdk;

import com.example.jdk18demo.proxy.jdk.v1.ChanelFactory;
import com.example.jdk18demo.proxy.jdk.v1.SellPerfume;
import com.example.jdk18demo.proxy.jdk.v2.SellProxyFactory;

import java.lang.reflect.Proxy;

public class XiaoMing2 {
    public static void main(String[] args) {
        //设置系统属性,生成$Proxy0的class文件,在项目根目录下增加com/sun/proxy目录
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        ChanelFactory chanelFactory = new ChanelFactory();
        SellProxyFactory sellProxyFactory = new SellProxyFactory(chanelFactory);
        SellPerfume sellPerfume =
                //生成代理对象需要用到Proxy类，它可以帮助我们生成任意一个代理对象
                (SellPerfume) Proxy.newProxyInstance(
                        //加载动态代理类的类加载器
                        chanelFactory.getClass().getClassLoader(),
                        //代理类实现的接口，可以传入多个接口
                        chanelFactory.getClass().getInterfaces(),
                        //指定代理类的调用处理程序，即调用接口中的方法时，会找到该代理工厂h，执行invoke()方法
                        sellProxyFactory
                );
        sellPerfume.sellPerfume(1999.99);
    }
}