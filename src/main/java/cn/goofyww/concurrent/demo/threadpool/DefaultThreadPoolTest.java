package cn.goofyww.concurrent.demo.threadpool;

public class DefaultThreadPoolTest {

    public static void main(String[] args) {

        DefaultThreadPool pool = new DefaultThreadPool();
        pool.execute(() -> {
            System.out.println("hello world");
        });

    }
}
