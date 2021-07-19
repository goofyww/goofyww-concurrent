package cn.goofyww.concurrent.method;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Domino implements Runnable {

    private Thread thread;

    public Domino(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
        System.out.printf("%s terminate. time【%s】\n", Thread.currentThread().getName(), new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }
}
