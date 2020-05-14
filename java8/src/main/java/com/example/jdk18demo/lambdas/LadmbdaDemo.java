package com.example.jdk18demo.lambdas;

import java.util.*;

public class LadmbdaDemo {
    public static void main(String[] args) {

        //Lambda 表达式之前
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, new Comparator<String>() {
            //创建了一个匿名内部类作为入参
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });

        //Lambda 表达 大括号 和 return
        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });

        //省略大括号 和 return
        Collections.sort(names, (String a, String b) -> b.compareTo(a));

        //省略 return
        Collections.sort(names, (a, b) -> b.compareTo(a));

        System.out.println("排序结果：" + names);

        names.sort(Collections.reverseOrder());

        System.out.println("排序结果：" + names);

        List<String> names2 = Arrays.asList("peter", null, "anna", "mike", "xenia");
        names2.sort(Comparator.nullsLast(String::compareTo)); //如何排序列表中有null的数据：null 值排在最后面
        System.out.println("如何排序列表中有null的数据值:" + names2);

        //List<String> names3 = null;

        List<String> names3 = new ArrayList<>(); //names3 不为null 时
        names3.add("c");
        names3.add("d");
        // 传 null 进到就得到 Optional.empty(), 非 null 就调用 Optional.of(obj).
        Optional.ofNullable(names3)
                //存在才对它做点什么
                .ifPresent(
                        list ->
                                //自然排序
                                list.sort(Comparator.naturalOrder()));

        System.out.println("null 值排序：" + names3);
    }
}
