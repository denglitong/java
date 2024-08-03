package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.ThreadUtils;

import java.time.Duration;
import java.util.Collection;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/23
 */
public class ThreadUtilsDemo {

    public static void main(String[] args) throws InterruptedException {
        // Thread 是 Java 运行时概念，和操作系统的线程是不同的概念
        Collection<Thread> threads = ThreadUtils.findThreadsByName("chrome");
        System.out.println(threads); // []

        Collection<ThreadGroup> threadGroups = ThreadUtils.getAllThreadGroups();
        threadGroups.forEach(System.out::println); // [name=main,maxpri=10]

        System.out.println(ThreadUtils.getSystemThreadGroup()); // [name=system,maxpri=10]

        System.out.println(ThreadUtils.getAllThreads());

        ThreadUtils.join(new MyThread(), Duration.ofSeconds(3));

        ThreadUtils.sleep(Duration.ofSeconds(3));
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("MyThread run...");
                ThreadUtils.sleep(Duration.ofSeconds(2));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
