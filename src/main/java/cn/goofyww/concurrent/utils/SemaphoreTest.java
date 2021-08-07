package cn.goofyww.concurrent.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 信号量使用
 */
public class SemaphoreTest {

    private static final int THREAD_COUNT = 30;

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(THREAD_COUNT);

    private static Semaphore s = new Semaphore(10);

    public static void main(String[] args) {

        for (int i = 0; i < THREAD_COUNT; i++) {
            THREAD_POOL.execute(() -> {
                try {
                    s.acquire();
                    System.out.println("save data " + System.currentTimeMillis());
                    s.release();
                } catch (InterruptedException e) {
                }
            });
        }

        THREAD_POOL.shutdown();

    }

}
