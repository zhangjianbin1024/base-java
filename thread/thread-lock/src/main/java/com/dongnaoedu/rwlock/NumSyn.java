package com.dongnaoedu.rwlock;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/09/20
 * 创建时间: 15:15
 * <p>
 * synchronized 锁测试 读写
 */
public class NumSyn implements IGoodsNum {

    private GoodsVo goods;

    public NumSyn(GoodsVo goods) {
        this.goods = goods;
    }


    /**
     * 当有多个线程时，在这里一起竞争锁，但只有一个线程可以执行业务逻辑
     *
     * @return
     */
    @Override
    public synchronized GoodsVo getGoodsNumber() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.goods;
    }

    /**
     * 当有多个线程时，在这里一起竞争锁，但只有一个线程可以执行业务逻辑
     *
     * @return
     */
    @Override
    public synchronized void setGoodsNumber(int changeNumber) {

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.goods.setGoodsVoNumber(changeNumber);

    }
}
