package cn.goofyww.concurrent.demo.dbpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionRunner implements Runnable {

    private int count;
    private AtomicInteger got;
    private AtomicInteger notGot;

    public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
        this.count = count;
        this.got = got;
        this.notGot = notGot;
    }

    @Override
    public void run() {
        try {
            ConnectionPoolTest.start.await();
        } catch (InterruptedException e) {
        }
        while (count > 0) {
            try {
                // 从线程池中获取连接，如果 1000ms 内无法获取到，将会返回 null
                // 分别统计连接获取的数量 got 和未获取到的数量 notGot
                Connection connection = ConnectionPoolTest.pool.fetchConnection(1000);
                if (connection != null) {
                    try {
                        connection.createStatement();
                        connection.commit();
                    } finally {
                        ConnectionPoolTest.pool.releaseConnection(connection);
                        got.incrementAndGet();
                    }
                } else {
                    notGot.incrementAndGet();
                }
            } catch (InterruptedException | SQLException e) {
            } finally {
                count--;
            }
        }
        ConnectionPoolTest.end.countDown();
    }

}
