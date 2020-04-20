package com.dongnaoedu.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/11/20
 * 创建时间: 17:36
 * <p>
 * 独占锁的实现
 * 同一时刻，只有一个线程可以拿到锁
 * <p>
 * 缺点：不能重入锁，在递归中，会出现死锁
 */
public class SingleLock implements Lock {

    // 静态内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 294483231570940207L;


        // 是否处于占用状态
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1; // 状态为1时，则说明被线程独占了锁
        }

        // 获取独占锁：当状态为0的时候获取锁
        @Override
        public boolean tryAcquire(int acquires) {
            assert acquires == 1; // Otherwise unused
            // 期望将 0 改为 1，这是个原子操作，0改为1成功，则索说明获取锁成功
            // 第二个线程，再次获取锁时，还会执行该操作，但此时的 state 的值已经改1了，所以原子操作失败，则获取不到锁
            // 总结：重进入锁是指任意线程在获取到锁之后能够再次获取该锁而不会被锁所阻塞
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread()); // 设置哪个线程拿到了独占锁
                return true;
            }
            return false;  //没有拿到独占锁,则返会 false
        }

        // 释放独占锁，将状态设置为0
        @Override
        protected boolean tryRelease(int releases) {
            assert releases == 1; // Otherwise unused
            if (getState() == 0) { // getState() 获取同步状态
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null); // 释放了锁，则设为null
            //设置同步状态，这里为什么不是原子操作，因为只有拿到锁的线程，才能释放锁，不会被多个线程设置
            // 并且，等待线程可以看到，锁释放了
            setState(0);
            return true;
        }

        // 返回一个Condition，每个condition都包含了一个condition队列
        Condition newCondition() {
            return new ConditionObject();
        }
    }

    // 仅需要将操作代理到Sync上即可
    private final Sync sync = new Sync();

    @Override
    public void lock() {
        // 只有一个线程可以得到锁
        sync.acquire(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }
}
