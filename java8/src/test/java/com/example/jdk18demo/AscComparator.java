package com.example.jdk18demo;

import java.util.Comparator;

public class AscComparator implements Comparator<Cat> {

    @Override
    public int compare(Cat stu1, Cat stu2) {

        // 根据成绩降序排列
        return stu1.getRecord() - stu2.getRecord();
    }

}