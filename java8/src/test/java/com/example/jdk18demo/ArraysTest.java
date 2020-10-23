package com.example.jdk18demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArraysTest {
    public static void main(String[] args) {
        int[] array = {1,2,3};
        int[] array2 = {1,2,3};
        System.out.println(array.length);
        List myList = Arrays.asList(array,array2);
        System.out.println(myList.size());

        List<Integer> collect = Arrays.stream(array).boxed().collect(Collectors.toList());
        collect.add(12);
        System.out.println(collect.size());
    }
}