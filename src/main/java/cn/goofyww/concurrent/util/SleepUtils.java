package cn.goofyww.concurrent.util;

import java.util.concurrent.TimeUnit;

public class SleepUtils {

    public static void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

    public static void milliSecond(long timout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timout);
        } catch (InterruptedException e) {
        }
    }

}
