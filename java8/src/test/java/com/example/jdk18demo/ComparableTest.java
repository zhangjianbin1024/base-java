package com.example.jdk18demo;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class ComparableTest {

    public static void main(String[] args) {
        List<Cat> catList = asList(new Cat("liming", 90),
                new Cat("xiaohong", 95),
                new Cat("zhoubin", 88),
                new Cat("xiaoli", 94)
        );
        // 排序前
        System.out.println(catList);

        Collections.sort(catList);
        // 排序后
        System.out.println(catList);

        //for (Cat student : catList) {
        //    System.out.println(student.equals(new Cat("xiaohong", 95)));
        //}
    }
}