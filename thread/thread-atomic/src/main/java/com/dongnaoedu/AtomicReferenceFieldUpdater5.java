package com.dongnaoedu;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 原子更新字段类
 * <p>
 * AtomicReferenceFieldUpdater：
 * AtomicIntegerFieldUpdater：
 * AtomicLongFieldUpdater
 */
public class AtomicReferenceFieldUpdater5 {
    public static void main(String[] args) {
        AtomicReferenceFieldUpdater updater =
                //处理的字段 name
                AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "name");

        // 处理的字段 old
        AtomicIntegerFieldUpdater<User> ageUpdater =
                AtomicIntegerFieldUpdater.newUpdater(User.class, "old");

        User user = new User("zhang", 34);

        System.out.println("old user name:" + user.name);

        updater.compareAndSet(user, user.name, "keke");

        System.out.println("now user name:" + user.name);

        System.out.println("old user age:" + ageUpdater.getAndIncrement(user));

        // 输出现在的年龄
        System.out.println("now user age:" + ageUpdater.get(user));

    }

    static class User {

        // 字段必须是 public volatile 修饰的
        public volatile String name;
        public volatile int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOld() {
            return old;
        }

        public void setOld(int old) {
            this.old = old;
        }
    }

}
