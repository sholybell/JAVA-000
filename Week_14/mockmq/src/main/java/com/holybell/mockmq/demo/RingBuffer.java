package com.holybell.mockmq.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RingBuffer {

    // ringBuffer的总长度，最大能够存储的成员数
    private int length;
    // ringBuffer内部存储的现有的成员数
    private int size;
    // 写指针,代表下一次操作执行的位置
    private int writePos;
    // 读指针,代表下一次操作执行的位置
    private int readPos;
    // 放置成员的循环数组，长度为length
    private Object[] list;

    private Lock lock = new ReentrantLock();

    /**
     * ringBuffer的构造函数<br>
     * 初始化各种参数和object数组
     *
     * @param length ringBuffer的总长度，最大能够存储的成员数
     */
    public RingBuffer(int length) {
        this.length = length;
        list = new Object[length];
        size = 0;
        writePos = 0;
        readPos = 0;
    }

    /**
     * 显示ringBuffer已满，是否还能插入新的元素
     *
     * @return 如果数组已满，不能插入，返回true<br>如果能插入，返回false
     */
    private boolean isFull() {
        if (size == length) {
            return true;
        }
        return false;
    }

    /**
     * 显示ringBuffer是否为空，是否还能取出元素
     *
     * @return 如果数组为空，不能取出元素，返回true <br> 如果能取出，返回false
     */
    private boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * 在ringBuffer中插入一个元素，在写指针处插入元素
     *
     * @param object 被插入的元素
     * @return 如果成功插入，返回true<br> 如果队列已满，返回false
     */
    public boolean put(Object object, long waitSec) {
        try {
            if (lock.tryLock(waitSec, TimeUnit.SECONDS)) {
                if (isFull()) {
                    //如果队列已满
                    return false;
                }
                if (writePos == length - 1) {
                    //如果这次写入的位置在队列的最后一个元素
                    list[writePos] = object;
                    //那么下次写入的位置为0
                    writePos = 0;
                } else {
                    //这次写入的位置不是队列最后一个元素，那么现在writePos处写入，然后++
                    list[writePos++] = object;
                }
                //ringBuffer中的成员数增加
                //size的处理在最后操作，防止size增加了，读操作却没有读到
                size++;
                return true;
            }
        } catch (Exception ex) {
            // DO NOTHING
        }
        return false;
    }

    /**
     * 从ringBuffer中取出一个元素
     *
     * @return 如果队列为空，返回null <br> 否则，返回读指针对应的元素
     */
    public synchronized Object take(long waitSec) {
        try {
            if (lock.tryLock(waitSec, TimeUnit.SECONDS)) {
                if (isEmpty()) {
                    return null;
                }
                Object result;
                if (readPos == length - 1) {
                    //如果这次取出的位置在队列的最后一个元素
                    result = list[readPos];
                    //将对于位置的元素清空
                    list[readPos] = null;
                    readPos = 0;
                } else {
                    result = list[readPos];
                    list[readPos++] = null;
                }
                size--;
                return result;
            }
        } catch (Exception ex) {
            // DO NOTHING
        }
        return false;
    }

    public void printRingBuffer() {
        System.out.println("开始打印环形缓冲区");
        System.out.println("length=" + length + ", size=" + size + ", writePos=" + writePos + ", readPos=" + readPos);
        for (int i = 0; i < length; i++) {
            System.out.println("区域位置坐标:" + i + "  :" + list[i]);
        }
        System.out.println("打印环形缓冲区结束");
        System.out.println();
    }


}