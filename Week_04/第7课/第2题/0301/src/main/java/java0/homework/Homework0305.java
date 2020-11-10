package java0.homework;

import java.util.concurrent.*;

public class Homework0305 {


    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(1);
        try {
            Future<Integer> future = es.submit(Homework0305::sum);
            System.out.println("异步计算结果为：" + future.get());
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            es.shutdown();
            es.awaitTermination(1, TimeUnit.MINUTES);
        }
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
