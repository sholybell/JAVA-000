package com.holybell.homework05.lesson10.q1;

/**
 * 内部类模式，内部类如果没有被使用不会立即加载，因此外部类被加载时，暂时不会创建单例对象
 */
public class Singleton05 {

    private static Singleton05 singleton;

    private static class SingletonHelper {

        static {
            singleton = new Singleton05();
        }
    }

    public static Singleton05 getSingleton() {
        return Singleton05.singleton;
    }
}
