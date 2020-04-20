package com.dongnaoedu.safeclass;

import java.util.ArrayList;
import java.util.List;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/14
 * 创建时间: 20:06
 * 不安全的发布
 * */
public class UnsafePublish2 {
    private List<String> list = new ArrayList<>();

    /*不安全的发布，将内部的线程不安全的list发布出去了*/
    public synchronized String getList(int i) {
        return list.get(i);
    }

    public synchronized void setList(List<String> list) {
        this.list = list;
    }
}
