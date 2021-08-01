package cn.goofyww.concurrent.base;

import cn.goofyww.concurrent.util.SleepUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程优先级
 * 结论：线程优先级不能作为程序正确性的依赖，
 * 操作系统可能会忽略对线程优先级的设定，
 * setPriority()方法并不可靠
 */
public class Priority {

    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) {
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread t = new Thread(job, "Thread:" + i);
            t.setPriority(priority);
            t.start();
        }
        notStart = false;
        SleepUtils.second(10);
        notEnd = false;
        for (Job job : jobs) {
            System.out.printf("Job Priority:%d, Count:%d\n", job.priority, job.jc);
        }
    }

    static class Job implements Runnable {

        private int priority;
        private long jc;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            while (notStart) {
                Thread.yield();
            }
            while (notEnd) {
                Thread.yield();
                jc++;
            }

        }
    }

}
