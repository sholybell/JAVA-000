package com.holybell.mockmq.v2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HQueue<T> {

    // TODO 1. 直接创建最大数组会导致创建多个Queue之后占用大量内存
    // TODO 2. 如果使用自动扩展数组长度，则会频繁发生数组拷贝
    private volatile HMessage<T>[] queue = new HMessage[10000];
    // 队列写指针，所有的生产者共享一个writePos
    private volatile int writePos = 0;
    // 队列读指针，每个消费者各自拥有自己的读指针
    private ThreadLocal<Integer> readPos = new ThreadLocal<>();
    // 由于生产者共享一个写指针，因此需要保证线程安全的锁
    private Lock lock = new ReentrantLock();

    public HMessage<T> consume() {
        int pos = readPos.get() == null ? 0 : readPos.get();
        // 读指针不应该读取到尚未插入数据的位置
        if (writePos == 0 || pos == writePos) {
            return null;
        }

        // 加锁，避免生产者生产完毕之后，数组内对象未维护完毕就开始获取，从而得到null
        lock.lock();
        HMessage<T> hMessage = queue[pos];
        lock.unlock();
//        System.out.println("readPos : " + pos + " writePos : " + writePos);
        readPos.set(pos + 1); // 自动提交机制，每次获取一个消息默认当前消息被正确处理
        return hMessage;
    }

    public boolean produce(HMessage<T> msg, long waitMillSec) {
        try {
            if (lock.tryLock(waitMillSec, TimeUnit.MILLISECONDS)) {
                queue[writePos++] = msg;
                return true;
            }
        } catch (Exception ex) {
            // DO NOTHING
        } finally {
            lock.unlock();
        }
        return false;
    }
}