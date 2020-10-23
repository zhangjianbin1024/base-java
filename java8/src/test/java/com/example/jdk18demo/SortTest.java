package com.example.jdk18demo;

public class SortTest {
    public static void main(String[] args) {
        Integer a = 10, b = 20, c = 30, d = 30;
        System.out.println(a.compareTo(b)); // 返回 -1 说明 a 要比 b 小
        System.out.println(c.compareTo(b)); // 返回  1 说明 c 要比 b 大
        System.out.println(d.compareTo(c)); // 返回  0 说明 d 和c  相等
    }
}