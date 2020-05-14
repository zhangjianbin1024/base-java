package com.example.jdk18demo.interfaces;

public interface Jdk8Interface {
    int MAX_SERVICE_TIME = 100;

    void test();

    /**
     * 接口内允许添加默认实现的方法
     */
    default void doSomething() {
        System.out.println("do something");
    }

    static void t1() {
        System.out.println("t1");
    }


    public static void main(String[] args) {
        System.out.println(Jdk8Interface.MAX_SERVICE_TIME);
        Jdk8Interface jdk8Interface = new Jdk8InterfaceImpl();
        //默认方法
        jdk8Interface.doSomething();
        //接口中的静态方法
        Jdk8Interface.t1();
        //子类实现的方法
        jdk8Interface.test();

    }
}


class Jdk8InterfaceImpl implements Jdk8Interface {
    @Override
    public void test() {
        System.out.println("test");

    }
}