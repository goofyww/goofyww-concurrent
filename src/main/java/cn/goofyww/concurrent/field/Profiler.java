package cn.goofyww.concurrent.field;

import cn.goofyww.concurrent.util.SleepUtils;

/**
 * ThreadLocal 的使用
 *
 * Profiler可以被复用在方法调用耗时统计的功能上，在方法的入口前执行 begin()方法，
 * 在方法调用后执行end()方法，好处是两个方法的调用不用在一个方法或者类中，比如在AOP（面向切面编程）中，
 * 可以在方法调用前的切入点执行begin()方法，而在方法调用后的切入点执行end()方法，这样依旧可以获得方法的执行耗时。
 */
public class Profiler {

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) {

        Profiler.begin();
        SleepUtils.second(1);
        System.out.printf("Cost: %d mills \n", Profiler.end());
    }

}
