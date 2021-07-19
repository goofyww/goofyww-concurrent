package cn.goofyww.concurrent.demo.dbpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个简单的数据库连接池实例
 */
public class ConnectionPoolTest {

    static ConnectionPool pool = new ConnectionPool(10);
    // 保证所有 ConnectionRunner 能够同时开始
    static CountDownLatch start = new CountDownLatch(1);
    // main 线程将会等待所有 ConnectionRunner 结束后才能继续执行
    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        // 线程数量，可以修改线程数量进行观察
        int threadCount = 20;
        end = new CountDownLatch(threadCount);

        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection: " + got);
        System.out.println("not got connection: " + notGot);
    }

}
