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