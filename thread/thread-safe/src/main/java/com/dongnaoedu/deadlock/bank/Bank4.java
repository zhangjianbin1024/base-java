package com.dongnaoedu.deadlock.bank;


import com.dongnaoedu.deadlock.bank.serivice.ITransfer;
import com.dongnaoedu.deadlock.bank.serivice.TryLockTransfer;


/**
 * 动脑学院-Mark老师
 * 创建日期：2017/08/30
 * 创建时间: 15:36
 */
public class Bank4 {

    private static class TransferThread extends Thread {
        private String name;
        private Account from;
        private Account to;
        private int amount;
        private ITransfer transfer;

        public TransferThread(String name, Account from, Account to,
                              int amout, ITransfer transfer) {
            this.name = name;
            this.from = from;
            this.to = to;
            this.amount = amout;
            this.transfer = transfer;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            try {
                transfer.transfer(from, to, amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Bank4 bank = new Bank4();

        Account zhangsan = new Account("zhangsan", 20000);
        Account Lisi = new Account("lisi", 20000);

        //动态顺序死锁 产生
        //ITransfer transfer = new NormalTransfer();
        // 动态顺序死锁 解决方法，保证正确的加锁
        //ITransfer transfer = new SafeTransfer();
        // 动态顺序死锁 解决方法
        ITransfer transfer = new TryLockTransfer();

        //定义两个线程，转账
        TransferThread zsToLisi = new TransferThread("zsToLisi", zhangsan, Lisi,
                2000, transfer);
        TransferThread lisiTozs = new TransferThread("lisiTozs", Lisi, zhangsan,
                4000, transfer);
        zsToLisi.start();
        lisiTozs.start();

    }

}
