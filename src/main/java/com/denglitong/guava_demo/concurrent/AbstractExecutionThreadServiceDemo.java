package com.denglitong.guava_demo.concurrent;

import com.google.common.util.concurrent.AbstractExecutionThreadService;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/7
 */
public class AbstractExecutionThreadServiceDemo extends AbstractExecutionThreadService {

    @Override
    protected void run() throws Exception {
        int num = 0;
        while (isRunning()) {
            System.out.println("execution running..." + num++);
            Thread.sleep(1000);
        }
    }

    @Override
    protected void startUp() throws Exception {
        System.out.println("execution startUp...");
    }

    @Override
    protected void shutDown() throws Exception {
        System.out.println("execution shutDown...");
    }

    @Override
    protected void triggerShutdown() {
        System.out.println("execution trigger shutDown...");
    }

    public static void main(String[] args) throws Exception {
        AbstractExecutionThreadServiceDemo service = new AbstractExecutionThreadServiceDemo();
        System.out.println(service.state()); // NEW

        service.startAsync(); // -> startUp
        System.out.println(service.state()); // STARTING

        Thread.sleep(100);
        System.out.println(service.state()); // RUNNING

        Thread.sleep(3000);
        service.stopAsync(); // -> triggerShutdown -> shutDown
        System.out.println(service.state()); // STOPPING
    }
}
