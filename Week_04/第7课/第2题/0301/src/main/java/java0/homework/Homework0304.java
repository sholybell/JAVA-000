package java0.homework;

public class Homework0304 {

    private static volatile boolean isFinish = false;   //可以替换为AtomicBoolean
    private static volatile int result = 0;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        new Thread(() -> {
            result = sum();
            isFinish = true;
        }).start();

        while (!isFinish) {
            // 等到isFinish为true，表示result赋值已经完毕，一个线程内部有序，主线程可见isFinish，那么就应该可以看见result的修改
        }

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
