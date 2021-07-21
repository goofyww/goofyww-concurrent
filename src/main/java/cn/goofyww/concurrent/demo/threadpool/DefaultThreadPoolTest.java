package cn.goofyww.concurrent.demo.threadpool;

public class DefaultThreadPoolTest {

    /** 拥有继承关系的 InheritableThreadLocal **/
    private static final InheritableThreadLocal<String> tl = new InheritableThreadLocal<String>();

    public static void main(String[] args) {

        tl.set("13001217708");
        DefaultThreadPool pool = new DefaultThreadPool();
        pool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + ":hello world ");
            System.out.println(tl.get());
        });

    }
}
