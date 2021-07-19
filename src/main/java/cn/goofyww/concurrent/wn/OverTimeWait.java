package cn.goofyww.concurrent.wn;

/**
 * 等待超时模式
 */
public class OverTimeWait {

    public static void main(String[] args) {

    }

    // 对当前对象加锁
    public synchronized Object get(String result, long mills) throws InterruptedException {
        long future = System.currentTimeMillis() + mills;
        long remaining = mills;
        // 当超时大于0 并且result返回值不满足要求
        while ((result == null) && remaining > 0) {
            // 异步做一些业务逻辑
            // 在超时时长内循环获取result结果
            // 获取到结果立即返回
            remaining = future - System.currentTimeMillis();
        }
        // 超时返回默认值
        return result;
    }

}
