package com.dongnaoedu;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/30
 * 创建时间: 20:54
 * <p>
 * 原子更新引用类型提供的类:
 * AtomicReference： 可以解决更新多个变量的问题
 * <p>
 * 例如：我有一杯水，放在桌上，但我走后，别人喝了这杯水，并又重新给杯子倒满了水，我回来看了一下，
 * 认为还是我的水，但其实这杯水，在我走后是有人动过的
 * <p>
 * AtomicStampedReference：解决ABA问题 ：关注这杯水有几个人动过
 * AtomicMarkableReference：解决ABA问题 ：关注这杯水有没有人动过
 */
public class AtomicRef3 {

    //AtomicReference： 可以解决更新多个变量的问题
    static AtomicReference<User> userAtomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        User user = new User("Mark", 25);
        userAtomicReference.set(user);

        //更新的值
        User updateUser = new User("Mike", 26);

        // 将对象user 改为 新对象updateUser 的值,这个过程是原子操作
        userAtomicReference.compareAndSet(user, updateUser);

        System.out.println(userAtomicReference.get().getName());
        System.out.println(userAtomicReference.get().getOld());
    }

    static class User {
        private String name;
        private int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public int getOld() {
            return old;
        }
    }

}
