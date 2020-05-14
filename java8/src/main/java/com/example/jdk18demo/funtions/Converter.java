package com.example.jdk18demo.funtions;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);

    default String t1() {
        return "23";
    }
}

class t1 {

    // 静态变量
    static int outerStaticNum;

    // 成员变量
    int outerNum;

    public static void main(String[] args) {
        int num = 1;

        //convert 方法的实现
        Converter<Integer, String> converter =
                (from -> {
                    String value = String.valueOf(from + num);
                    //Lambda 表达式中对成员变量和静态变量拥有读写权限：
                    outerStaticNum = 23;
                    outerStaticNum = 11;
                    //带有默认实现的接口方法，是不能在 lambda 表达式中访问的
                    //t1();
                    System.out.println("自定义converter convert 方法入参值：" + from);
                    System.out.println("自定义converter convert 方法返回的值：" + value);
                    return value;
                });
        converter.convert(23); // convert 方法的入参
        System.out.println("自定义converter t1 默认方法调用：" + converter.t1());


        //predicate 是一个可以指定入参类型，并返回 boolean 值的函数式接口
        Predicate<String> predicate = s -> {
            boolean b = s.length() > 0;
            return b;
        }; //定义 test 方法的实现

        System.out.println("Predicate test 方法值:" + predicate.test("123")); //test 方法输入值


        //function 提供一个原料，他给生产一个最终的产品
        Function<String, Integer> toInteger = Integer::valueOf; // 定义 apply 的实现
        Integer apply = toInteger.apply("123"); // apply 方法的入参
        System.out.println("Function apply 方法的值:" + apply);

        //Supplier 它不接受入参，直接为我们生产一个指定的结果
        Supplier<Object> result = Object::new;
        Object s = result.get();
        System.out.println("Supplier get 方法的值：" + s);

        //Consumer 消费者 需要提供入参，用来被消费
        Consumer<Integer> tConsumer = p -> System.out.println(p); // 定义 accept 的实现
        tConsumer.accept(123); // accept 方法的入参

        //Comparator
        Comparator<Integer> comparator =
                (a1, a2) -> a1.compareTo(a2); // compare 定义实现
        int compare = comparator.compare(1, -1);//compare 入参
        System.out.println("Comparator compare 方法值:" + compare);


    }
}