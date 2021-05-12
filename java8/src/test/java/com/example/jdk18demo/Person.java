package com.example.jdk18demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Person {
    public String name;// 姓名
    public int age;// 年龄

    public Person() {
        super();
    }

    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String showInfo() {
        return "name=" + name + ", age=" + age;
    }
}

class Student extends Person {
    private String className;// 班级
    private String address;// 住址

    public Student() {
        super();
    }

    private void t1(){

    }

    public Student(String name, int age, String className, String address) {
        super(name, age);
        this.className = className;
        this.address = address;
    }

    public Student(String className) {
        this.className = className;
    }

    public String toString() {
        return "姓名：" + name + ",年龄：" + age + ",班级：" + className + ",住址："
                + address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

class TestRelect {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Class student = null;
        try {
            student = Class.forName("com.example.jdk18demo.Student");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 获取对象的所有公有属性。
        System.out.println("--------获取对象的所有公有属性-------------");
        Field[] fields = student.getFields();
        for (Field f : fields) {
            System.out.println(f);
        }
        System.out.println("-------获取对象所有属性，但不包含继承的--------------");
        // 获取对象所有属性，但不包含继承的。
        Field[] declaredFields = student.getDeclaredFields();
        for (Field df : declaredFields) {
            System.out.println(df);
            Class<?> type = df.getType();
            System.out.println(type);
        }

        // 获取对象的所有公共方法
        System.out.println("--------获取对象的所有公共方法-------------");
        Method[] methods = student.getMethods();
        for (Method m : methods) {
            System.out.println(m);
        }
        System.out.println("--------获取对象所有方法，但不包含继承的-------------");
        // 获取对象所有方法，但不包含继承的
        Method[] declaredMethods = student.getDeclaredMethods();
        for (Method dm : declaredMethods) {
            System.out.println(dm);
        }

        // 获取对象所有的公共构造方法
        Constructor[] constructors = student.getConstructors();
        for (Constructor c : constructors) {
            System.out.println(c);
        }
        System.out.println("---------------------");
        // 获取对象所有的构造方法
        Constructor[] declaredConstructors = student.getDeclaredConstructors();
        for (Constructor dc : declaredConstructors) {
            System.out.println(dc);
        }

        Class c = Class.forName("com.example.jdk18demo.Student");
        Student stu1 = (Student) c.newInstance();
        // 第一种方法，实例化默认构造方法，调用set赋值
        stu1.setAddress("河北石家庄");
        System.out.println(stu1);

        // 第二种方法 取得全部的构造函数 使用构造函数赋值
        Constructor<Student> constructor = c.getConstructor(String.class,
                int.class, String.class, String.class);
        Student student2 = (Student) constructor.newInstance("cxuan", 24, "六班", "石家庄");
        System.out.println(student2);

        /**
         * 獲取方法并执行方法
         */
        Method show = c.getMethod("showInfo");//获取showInfo()方法
        Object object = show.invoke(student2);//调用showInfo()方法


        System.out.println("====================>>");
        System.out.println(Student.class.cast(student2));

        ClassLoader classLoader = Student.class.getClassLoader();

        System.out.println(classLoader.toString());
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());
    }
}