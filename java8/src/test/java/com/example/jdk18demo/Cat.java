package com.example.jdk18demo;

public class Cat implements Comparable<Cat> {

    private String name;
    private Integer record;

    public Cat() {
    }

    public Cat(String name, int record) {
        this.name = name;
        this.record = record;
    }

    @Override
    public boolean equals(Object obj) {
        Cat cat = (Cat) obj;
        // 拿名字和成绩进行对比
        return this.name.equals(cat.name)
                && this.record == cat.record;
    }

    @Override
    public int compareTo(Cat stu) {
        // 当返回值的是自身减去参数（即this - o）是正序排序，从 小到大
        // 当返回值的是参数减去自身（即o - this ）则是逆序排序,从大到小
        return stu.record.intValue() - this.record.intValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Student{");
        sb.append("name='").append(name).append('\'');
        sb.append(", record=").append(record);
        sb.append('}');
        return sb.toString();
    }
}