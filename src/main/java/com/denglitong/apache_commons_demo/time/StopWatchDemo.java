package com.denglitong.apache_commons_demo.time;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/27
 */
public class StopWatchDemo {

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = StopWatch.createStarted();
        task1();
        stopWatch.split();
        System.out.println(stopWatch.formatSplitTime()); // 00:00:01.234
        System.out.println(stopWatch.formatTime()); // 00:00:01.246

        task2();
        System.out.println(stopWatch.formatSplitTime()); // 00:00:01.234
        System.out.println(stopWatch.formatTime()); // 00:00:03.594

        stopWatch.unsplit();
        task2();
        stopWatch.split();
        System.out.println(stopWatch.formatSplitTime()); // 00:00:05.941
        System.out.println(stopWatch.formatTime()); // 00:00:05.941

        stopWatch.stop();
        System.out.println(stopWatch.getStopTime());  // 1630069576243
        System.out.println(stopWatch.getStartTime()); // 1630069570297
        System.out.println(stopWatch.getTime());      //          5945

        stopWatch.reset();
        stopWatch.start();
        task1();
        System.out.println(stopWatch.formatTime()); // 00:00:01.234
        task2();
        System.out.println(stopWatch.formatTime()); // 00:00:03.580
        stopWatch.suspend();
        task2();
        System.out.println(stopWatch.formatTime()); // 00:00:03.580
        stopWatch.resume();
        task1();
        System.out.println(stopWatch.formatTime()); // 00:00:04.818
    }

    private static void task1() throws InterruptedException {
        Thread.sleep(1234);
    }

    private static void task2() throws InterruptedException {
        Thread.sleep(2345);
    }
}
