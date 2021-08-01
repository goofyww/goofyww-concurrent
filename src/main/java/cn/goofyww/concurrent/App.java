package cn.goofyww.concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 初识多线程
 * Java 程序天生就是多线程
 */
public class App {

    /**
     * main
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");

        // 获得 Java 线程管理 MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程的堆栈信息
        ThreadInfo[] threadInfoArr = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，打印 线程id 和 线程名称
        for (ThreadInfo threadInfo : threadInfoArr) {
            System.out.printf("[%s] %s\n", threadInfo.getThreadId(), threadInfo.getThreadName());
        }

        /**
         * Hello World!
         * [5] Monitor Ctrl-Break
         * [4] Signal Dispatcher    // 分发处理发送给 JVM 信号的线程
         * [3] Finalizer            // 调用对象 finalize 方法的线程
         * [2] Reference Handler    // 清除 Reference 的线程
         * [1] main                 // 主线程，用户程序入口
         */

    }

}
