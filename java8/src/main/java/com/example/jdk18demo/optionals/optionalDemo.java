package com.example.jdk18demo.optionals;

import java.util.HashMap;

public class optionalDemo {
    public static void main(String[] args) {

        //Optional<String> b = Optional.of("b");
        //if (b.isPresent()) {
        //    String s = b.get();
        //    System.out.println("值不为空");
        //}
        //Optional<Object> empty = Optional.empty();
        //System.out.println(empty.orElse("fallback"));
        //
        ////如果 optional 对象中有值时，就将值转为小写
        //Optional.ofNullable("ABC").ifPresent(s -> System.out.println(s.toLowerCase()));


        HashMap<String, String> map = new HashMap<>();

        String s = map.get(null);
        System.out.println(s);
    }
}
