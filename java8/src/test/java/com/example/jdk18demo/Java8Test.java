package com.example.jdk18demo;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangjianbin
 * @date 2021年08月05日11:15
 */
public class Java8Test {
    @Test
    public void t1() {
        List<Item> items = Arrays.asList(
                new Item("apple", 10, new BigDecimal(23.5)),
                new Item("apple", 20, new BigDecimal(32.5)),
                new Item("orange", 30, new BigDecimal(20)),
                new Item("orange", 20, new BigDecimal(32.5)),
                new Item("orange", 10, new BigDecimal(63.5)),
                new Item("orange", 50, new BigDecimal(41.5)),
                new Item("peach", 20, new BigDecimal(26.5)),
                new Item("peach", 30, new BigDecimal(32.5)),
                new Item("peach", 40, new BigDecimal(24.5)),
                new Item("peach", 10, new BigDecimal(12.5))
        );
        Map<String, List<Item>> java8Befor = java8Befor(items);

        // 使用java8特性
        Map<String, List<Item>> java8After = items.stream()
                .collect(Collectors.groupingBy(Item::getName));

        //另一种写法
            /*Map<String, List<Item>> alarmSourceMap1 = items
                            .stream().collect(Collectors.toMap(Item::getName,
                                    p ->{
                                        List<Item> list = new ArrayList<>();
                                        list.add(p);
                                        return list;
                                    },(List<Item> value1, List<Item> value2) -> {
                                        value1.addAll(value2);
                                        return value1;
                                    }));*/

        System.out.println("********************java8Befor************************");
        for (Map.Entry entry : java8Befor.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        System.out.println("*******************java8After*************************");

        for (Map.Entry entry : java8After.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private Map<String, List<Item>> java8Befor(List<Item> items) {
        //不使用java8特性的写法
        Map<String, List<Item>> resultMap = new HashMap<>(1024);
        for (Item item : items) {
            List<Item> tempList = resultMap.get(item.getName());
            //如果取不到数据,那么直接new一个空的ArrayList*
            if (tempList == null) {
                tempList = new ArrayList<>();
                tempList.add(item);
                resultMap.put(item.getName(), tempList);
            } else {
                //某个alarmSourceInfoEntity之前已经存放过了,则直接追加数据到原来的List里*
                tempList.add(item);
            }
        }
        return resultMap;
    }
}