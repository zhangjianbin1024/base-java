package com.example.jdk18demo.proxy.jdk.v2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SellProxyFactory implements InvocationHandler {
    /**
     * 代理的真实对象
     */
    private Object realObject;

    public SellProxyFactory(Object realObject) {
        this.realObject = realObject;
    }

    /**
     * invoke() 方法是一个代理方法，也就是说最后客户端请求代理时，执行的就是该方法
     *
     * @param proxy  代理对象
     * @param method 真正执行的方法
     * @param args   调用第二个参数 method 时传入的参数列表值
     *
     * @return
     *
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        doSomethingBefore();
        Object obj = method.invoke(realObject, args);
        doSomethingAfter();
        return obj;
    }

    private void doSomethingAfter() {
        System.out.println("执行代理后的额外操作...");
    }

    private void doSomethingBefore() {
        System.out.println("执行代理前的额外操作...");
    }

}