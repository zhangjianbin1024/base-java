package com.dongnaoedu;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/12/03
 * 创建时间: 22:08
 */
public class UseChm1 {

    HashMap<String, String> hashMap = new HashMap<>();

    ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<>();

    /**
     * key 不存在时插入元素
     *
     * @param key
     * @param value
     * @return
     */
    public String putIfAbsent(String key, String value) {
        synchronized (hashMap) { // 线程安全
            if (hashMap.get(key) == null) {
                return hashMap.put(key, value);
            } else {
                return hashMap.get(key);
            }
        }
    }

    /**
     * key 不存在时插入元素
     *
     * @param key
     * @param value
     * @return
     */
    public String useChm(String key, String value) {
        return chm.putIfAbsent(key, value);
    }


    public static void main(String[] args) {
        UseChm1 useChm = new UseChm1();
        useChm.putIfAbsent("key", "value");
        useChm.putIfAbsent("key", "value");

        useChm.useChm("key", "value");
        useChm.useChm("key", "value");
        System.out.println(useChm.hashMap);
        System.out.println(useChm.chm);

        useChm.chm.remove("key");
        System.out.println(useChm.chm);
    }

}
