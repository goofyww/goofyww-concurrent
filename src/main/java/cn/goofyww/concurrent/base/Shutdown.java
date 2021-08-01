package cn.goofyww.concurrent.base;

import cn.goofyww.concurrent.util.SleepUtils;

/**
 * 安全地终止线程
 */
public class Shutdown {

    public static void main(String[] args) {
        Runner one = new Runner();
        Thread t1 = new Thread(one, "CountThread");
        t1.start();

        // 休眠1秒，main 线程对 CountThread 进行中断，使 CountThread 能够感知中断而结束
        SleepUtils.second(1);
        t1.interrupt();

        Runner two = new Runner();
        t1 = new Thread(two, "CountThread");
        t1.start();

        // 休眠1秒，main 线程对 Runner two 进行取消，使 CountThread 能够感知 on 为 false 而结束
        SleepUtils.second(1);
        two.cancel();
    }

    private static class Runner implements Runnable {

        private long i;
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Count i = " + i);
        }

        public void cancel() {
            on = false;
        }
    }
}

