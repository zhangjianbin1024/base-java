package com.example.jdk18demo;

import java.util.Arrays;
import java.util.List;

public class SafeVarargs {

    // 这其实不是一个安全的类型检查
    //@SafeVarargs
    @java.lang.SafeVarargs
    static void m(List<String>... lists) {
        // 先会存储到 array[0] 的位置
        Object[] array = lists;
        List<Integer> tmpList = Arrays.asList(42);
        // array[0] 又保存了tmpList(Integer)进行覆盖
        // tmpList是一个List对象（类型已经擦除），赋值给Object类型的对象是允许的（向上转型），
        // 能够编译通过
        array[0] = tmpList;
        // 实际取出来的应该是 42
        String s = lists[0].get(0);

    }

    public static void main(String[] args) {
        List<String> list1 = Arrays.asList("one", "two");
        m(list1);
    }
}