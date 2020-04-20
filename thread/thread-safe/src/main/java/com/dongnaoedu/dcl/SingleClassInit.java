package com.dongnaoedu.dcl;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/14
 * 创建时间: 22:07
 * 延迟类占位符
 */
public class SingleClassInit {
    private String name;
    private SingleClassInit(){}

    private static class InstanceHolder{
        public static SingleClassInit instance = new SingleClassInit();
    }

    public static SingleClassInit getInstance(){
        return InstanceHolder.instance;
    }


}


