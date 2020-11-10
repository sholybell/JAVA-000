package java0.homework;

import java.util.concurrent.*;

public class Homework0307 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);
        try {
            long start = System.currentTimeMillis();
            cs.submit(Homework0307::sum);
            System.out.println("异步计算结果为：" + cs.take().get());
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } finally {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
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
