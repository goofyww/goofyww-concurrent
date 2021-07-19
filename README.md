# goofyww-concurrent
Those things of concurrent

## 等待-通知的经典范式

-- 等待方遵循如下原则
1）获取对方的锁
2）如果条件不满足，那么调用对象的 wait() 方法，被通知后仍要检查条件
3）条件满足则执行对应的逻辑

```
# 伪代码
synchronized(对象){
    while(条件不满足){
        对象.wait();
    }
    对应的处理逻辑
}
```

-- 通知方遵循如下原则
1）获得对象的锁
2）改变条件
3）通知所有等待在对象上的线程

```
# 伪代码
synchronized(对象){
    改变条件
    对象.notifyAll();
}
```

## Thread.join()方法源码
* 当线程终止时，会调用线程自身的 `notifyAll()` 方法，会通知所有等待在线程对象上的线程
-- 等待/通知 经典范式
1）加锁
2）循环
3）处理逻辑

```
// 加锁当前线程对象
public final synchronized void join() throws InterruptedException {
    // 条件不满足，继续等待
    while(isAlive){
        wait(0)
    }
    // 条件符合，方法返回
}
```

## 等待超时模式
-- 等待超时模式是在 等待/通知范式 基础上增加超时控制，使得该模式想必原有范式更具有灵活性，
因为即使方法执行时间过长，也不会"永久"阻塞调用者，而是会按照调用者的要求"按时"返回

* 等待持续时间： REMAINING = T
*    超时时间： FUTURE = NEW + T