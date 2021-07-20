package cn.goofyww.concurrent.demo.base;

/**
 * 多线程真的好吗？
 * 上下文切换对于程序执行效率的影响
 */
public class UpDownDemo {

    private static final long count = 10000000L;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("============ 串 行 ============");
        c();
        System.out.println("============ 并 行 ============");
        b();

    }

    /**
     * 并行
     */
    public static void b() throws InterruptedException {

        long startTimeMillis = System.currentTimeMillis();

        Thread t = new Thread(() -> {
            int a = 0;
            for (int i = 0; i < count; i++) {
                a += 5;
            }
        });

        t.start();

        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }

        t.join();
        System.out.printf("并行方法耗时（ms）：%d \n ,b=%d", System.currentTimeMillis() - startTimeMillis, b);
    }

    /**
     * 串行
     */
    public static void c() {
        long startTimeMillis = System.currentTimeMillis();


        /**
         * 任务一
         */
        int a = 0;
        for (int i = 0; i < count; i++) {
            a += 5;
        }

        /**
         * 任务二
         */
        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }

        System.out.printf("串行方法耗时（ms）：%d \n，a=%d ,b=%d \n", System.currentTimeMillis() - startTimeMillis, a, b);
    }


}
