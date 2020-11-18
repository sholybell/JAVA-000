package com.holybell.homework05.lesson10.q1;

/**
 * 饿汉式单例模式，一旦类被加载立马创建单例对象，但创建的太早了，可能并不需要使用单例对象
 */
public class Singleton04 {

    private static Singleton04 singleton = new Singleton04();

    public static Singleton04 getSingleton() {
        return singleton;
    }
}
