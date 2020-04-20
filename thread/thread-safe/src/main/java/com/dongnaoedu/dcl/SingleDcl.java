package com.dongnaoedu.dcl;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/14
 * 创建时间: 22:01
 * 懒汉式单例-双重检查
 */
public class SingleDcl {

    // volatile 不加这个关键字，则是线程不安全的
    private volatile static SingleDcl single;

    private SingleDcl() {
    }

    public static SingleDcl getInstance() {
        if (null == single) {
            synchronized (SingleDcl.class) {
                if (single == null) {
                    single = new SingleDcl();
                }
            }
        }
        return single;
    }


}
