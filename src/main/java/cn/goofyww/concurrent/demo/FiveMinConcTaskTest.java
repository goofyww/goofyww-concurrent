package cn.goofyww.concurrent.demo;

import cn.goofyww.concurrent.util.SleepUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 有一个任务列表，假设存在了一个 slice中,
 * 需要每 5 分钟执行一次全部的任务，并且要并发执行，每次并发执行任务数不能超过 3 最后将执行的结果一次输出
 */
public class FiveMinConcTaskTest {

    private static final int tc = 12;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(tc);

    private static List<Job> jobs = new ArrayList<>(tc);

    public static Semaphore smp = new Semaphore(3);

    private static CountDownLatch start = new CountDownLatch(1);

    static {
        for (int i = 0; i < tc; i++) {
            Job job = new Job(start);
            jobs.add(job);
        }
    }

    /**
     * 5分钟并发任务，要求指定线程数并发执行
     */
    public static void fiveMinConcurrentTask() {
        for (Job job : jobs) {
            threadPool.execute(job);
        }
        start.countDown();
        threadPool.shutdown();
    }

    public static void main(String[] args) {
        fiveMinConcurrentTask();
    }

    static class Job implements Runnable {

        private CountDownLatch start;

        public Job(CountDownLatch start) {
            this.start = start;
        }

        public void run() {
            try {
                start.await();
                smp.acquire();
                System.out.println("save data " + System.currentTimeMillis());
                SleepUtils.second(1L);
                smp.release();
            } catch (InterruptedException e) {
            }
        }

    }

}
