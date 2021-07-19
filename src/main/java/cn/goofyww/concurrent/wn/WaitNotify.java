package cn.goofyww.concurrent.wn;

import cn.goofyww.concurrent.util.SleepUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 多线程
 * 通知机制
 */
public class WaitNotify {

    private static boolean flag = true;
    private static final Object lock = new Object();

    public static void main(String[] args) {

        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        SleepUtils.second(1);
        Thread notifyThread = new Thread(new Notify(), "notifyThread");
        notifyThread.start();
    }

    // 消费者
    static class Wait implements Runnable {

        @Override
        public void run() {
            // 加锁，拥有lock的 Monitor
            synchronized (lock) {
                // 当条件不满足时，继续wait，同时释放了lock的锁
                if (flag) {
                    try {
                        System.out.printf("%s flag is true. wait @ %s \n", Thread.currentThread(), new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();//释放了lock的锁，此操作会使 lock 从 blocked 转化为 waiting，由此其他线程才可以获取到这把锁
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            // 条件满足时，完成工作
            System.out.printf("%s flag is false. running @ %s \n", Thread.currentThread(), new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }

    }

    // 通知者
    static class Notify implements Runnable {

        @Override
        public void run() {

            // 加锁，拥有lock的Monitor
            synchronized (lock) {
                // 获取 lock 的锁，然后进行通知，通知时不会释放 lock 的锁
                // 直到当前线程释放了 lock 后，WaitThread 才能从 wait 方法中返回
                System.out.printf("%s hold lock. notify @ %s \n", Thread.currentThread(), new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }

            // 再次加锁
            synchronized (lock) {
                System.out.printf("%s hold lock again. sleep @ %s \n", Thread.currentThread(), new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
            }
        }
    }

}
