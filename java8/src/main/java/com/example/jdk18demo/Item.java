package com.example.jdk18demo;

import java.math.BigDecimal;

/**
 * @author zhangjianbin
 * @date 2021年08月05日11:16
 */
public class Item {
    private String name;
    private int age;
    private BigDecimal bigDecimal;

    public Item(String name, int age, BigDecimal bigDecimal) {
        this.name = name;
        this.age = age;
        this.bigDecimal = bigDecimal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Item{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", bigDecimal=").append(bigDecimal);
        sb.append('}');
        return sb.toString();
    }
}