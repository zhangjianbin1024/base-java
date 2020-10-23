package com.example.jdk18demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComparatorTest {

    public static void main(String[] args) {
        List<Cat> catList = Arrays.asList(new Cat("liming", 90),
                new Cat("xiaohong", 95),
                new Cat("zhoubin", 88),
                new Cat("xiaoli", 94)
        );
        // 1. 可以实现自己的外部接口进行排序
        Collections.sort(catList);

        System.out.println(catList);

        // 2、 可以匿名内部类实现自定义排序
        //Collections.sort(catList, (o1, o2) -> o2.getRecord() - o1.getRecord());
        //System.out.println(catList);
    }
}