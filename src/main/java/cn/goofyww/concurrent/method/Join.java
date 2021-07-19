package cn.goofyww.concurrent.method;

import cn.goofyww.concurrent.util.SleepUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Therad.join()的使用
 */
public class Join {

    public static void main(String[] args) {

        Thread previous = Thread.currentThread();
        System.out.printf("%s terminate. time【%s】\n", Thread.currentThread().getName(), new SimpleDateFormat("HH:mm:ss").format(new Date()));
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Domino(previous), String.valueOf(i));
            t.start();
            previous = t;
        }
        SleepUtils.second(5);
        System.out.printf("%s terminate. time【%s】\n", Thread.currentThread().getName(), new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }
}
