package com.example.jdk18demo.proxy.jdk;

import com.example.jdk18demo.proxy.jdk.v1.ChanelFactory;
import com.example.jdk18demo.proxy.jdk.v1.XiaoHongSellProxy;

public class XiaoMing1 {
    public static void main(String[] args) {
        ChanelFactory factory = new ChanelFactory();
        XiaoHongSellProxy proxy = new XiaoHongSellProxy(factory);
        proxy.sellPerfume(1999.99);
    }
}