package com.example.jdk18demo;

import org.openjdk.jol.info.ClassLayout;

public class HeapMemory {
    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        MyItem myItem = new MyItem();
        System.out.println(ClassLayout.parseInstance(myItem).toPrintable());
    }

    public static class MyItem {
        byte i = 0;
    }
}