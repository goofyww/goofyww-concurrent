package cn.goofyww.concurrent.demo.dbpool;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Objects;

public class ConnectionPool {

    private LinkedList<Connection> pool;

    public ConnectionPool(int initialSize) {
        assert initialSize > 0;
        pool = new LinkedList<>();
        for (int i = 0; i < initialSize; i++) {
            pool.addLast(ConnectionDriver.createConnection());
        }
    }

    // 归还连接
    public void releaseConnection(Connection connection) {
        if (!Objects.isNull(connection) && !Objects.isNull(pool)) {
            synchronized (pool) {
                // 连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    // 获取连接
    public Connection fetchConnection(long mills) throws InterruptedException {
        assert !Objects.isNull(pool);
        synchronized (pool) {
            // 完全超时
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }

}
