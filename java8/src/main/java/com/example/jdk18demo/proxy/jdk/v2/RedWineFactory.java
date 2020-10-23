package com.example.jdk18demo.proxy.jdk.v2;

/**
 * 红酒供应商
 */
public class RedWineFactory implements SellWine {

    @Override
    public void sellWine(double price) {
        System.out.println("成功售卖一瓶红酒，价格：" + price + "元");
    }

}