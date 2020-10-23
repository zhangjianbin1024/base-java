package com.example.jdk18demo.proxy.jdk;

import com.example.jdk18demo.proxy.jdk.v2.RedWineFactory;
import com.example.jdk18demo.proxy.jdk.v2.SellProxyFactory;
import com.example.jdk18demo.proxy.jdk.v2.SellWine;

import java.lang.reflect.Proxy;

public class XiaoMing3 {
    public static void main(String[] args) {
        // 实例化一个红酒销售商
        RedWineFactory redWineFactory = new RedWineFactory();
        // 实例化代理工厂，传入红酒销售商引用控制对其的访问
        SellProxyFactory sellProxyFactory = new SellProxyFactory(redWineFactory);
        SellWine sellWineProxy =
                // 实例化代理对象，该对象可以代理售卖红酒
                (SellWine) Proxy.newProxyInstance(
                        redWineFactory.getClass().getClassLoader(),//加载动态代理类的类加载器
                        redWineFactory.getClass().getInterfaces(), //代理类实现的接口，可以传入多个接口
                        sellProxyFactory //指定代理类的调用处理程序，即调用接口中的方法时，会找到该代理工厂h，执行invoke()方法
                );
        sellWineProxy.sellWine(1999.99);
    }
}