package com.example.jdk18demo.streams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StreamDemo {
    public static void main(String[] args) {
        //Stream 对一个包含一个或多个元素的集合做各种操作
        //终端操作会返回一个结果，而中间操作会返回一个 Stream 流
        //Stream 流支持同步执行，也支持并发执行

        ArrayList<String> list = new ArrayList<>();
        list.add("hd");
        list.add("ws");
        list.add("cs");

        //Filter 过滤,入参是一个 Predicate,
        // Predicate 是一个断言的中间操作,它能够帮我们筛选出我们需要的集合元素
        list.stream()
                .filter(s -> s.startsWith("hd"))
                //foreach 是一个终端操作，它的返参是 void, 我们无法对其再次进行流操作
                .forEach(s -> System.out.println("过滤 Filter:" + s));


        //Sorted 排序
        list.stream()
                .sorted()
                .filter(s -> s.endsWith("s"))
                .forEach(s -> {
                    String s1 = s.toUpperCase();
                    System.out.println("排序 Sorted:" + s1);
                });


        //Map 转换
        //中间操作 Map 能够帮助我们将 List 中的每一个元素做功能处理
        list.stream()
                .map(s -> {
                    String s1 = s.toUpperCase();
                    return s1;
                })
                .sorted((a, b) -> {
                    return a.compareTo(b);
                })
                .forEach(s -> System.out.println("转换 Map:" + s));


        //Match 匹配
        //通过 match, 我们可以方便的验证一个 list 中是否存在某个类型的元素
        boolean isContains = list.stream() //集合中 String 是否包含 s 的字符串
                .anyMatch(s -> s.contains("s"));
        System.out.println("匹配 anyMatch:" + isContains);

        //Count 计数
        long h = list.stream()
                .filter(s -> s.startsWith("h"))
                .count();
        System.out.println("计数 Count:" + h);


        //Reduce 中文翻译为：减少、缩小。通过入参的 Function
        //将 list 归约成一个值。它的返回类型是 Optional 类型
        Optional<String> reduce = list.stream()
                .sorted()
                .reduce((a, b) -> {
                    String s = a.toUpperCase();
                    String s1 = b.toUpperCase();
                    return s + s1;
                });
        reduce.ifPresent(x -> System.out.println("reduce:" + x));

        //Parallel-Streams 并行流
        //顺序流操作是单线程操作，而并行流是通过多线程来处理的

        int max = 10000;
        ArrayList<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        long start = System.nanoTime();
        long count = values.stream().sorted().count();
        long end = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(start - end);
        System.out.println(String.format("stream 顺序流排序耗时: %d ms", millis));

        start = System.nanoTime();
        count = values.parallelStream().sorted().count();
        end = System.nanoTime();
        millis = TimeUnit.NANOSECONDS.toMillis(start - end);
        System.out.println(String.format("parallelStream 并行流排序耗时: %d ms", millis));

        //Map 集合
        //Map 是不支持 Stream 流的
        //可以对其 key, values, entry 使用 流操作
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            //putIfAbent() 方法在 put 之前
            //会判断 key 是否已经存在，存在则直接返回 value, 否则 put, 再返回 value
            map.putIfAbsent(i, "val:" + i);
        }
        map.forEach((k, v) -> {
            System.out.println("Map 集合===>>" + "key：" + k + ";" + "value:" + v);
        });

        //computeIfPresent(), 当 key 存在时，才会做相关处理
        //如下：对 key 为 3 的值，内部会先判断值是否存在，存在，则做 value + key 的拼接操作
        map.computeIfPresent(3, (num, val) -> val + num);
        System.out.println("执行 computeIfPresent 方法后1:" + map.get(3));

        //先判断 key 为 9 的元素是否存在，存在，则做删除操作
        map.computeIfPresent(9, (num, val) -> null);
        System.out.println("执行 computeIfPresent 方法后2:" + map.get(9));

        //只有当给定的 key 和 value 完全匹配时，才会执行删除操作
        map.remove(3, "val:3");
        System.out.println("map remove:" + map.get(3));

        //若 key 42 不存在，则返回 not found
        String not_found = map.getOrDefault(42, "not found");
        System.out.println("map getOrDefault:" + not_found);

        //merge 方法，会先判断进行合并的 key 是否存在，不存在，则会添加元素
        map.merge(9, "val:9", (value, newValue) -> value.concat(newValue));
        System.out.println("map merge:" + map.get(9));


    }
}
