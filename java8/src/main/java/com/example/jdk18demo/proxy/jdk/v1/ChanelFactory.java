package com.example.jdk18demo.proxy.jdk.v1;

/**
 * 定义香奈儿（Chanel）香水提供商
 */
public class ChanelFactory implements SellPerfume {
    @Override
    public void sellPerfume(double price) {
        System.out.println("成功购买香奈儿品牌的香水，价格是：" + price + "元");
    }
}