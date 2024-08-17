package com.denglitong.guava_demo.concurrent;

import com.google.common.util.concurrent.AbstractScheduledService;

import java.time.Duration;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/7
 */
public class AbstractScheduledServiceDemo extends AbstractScheduledService {

    @Override
    protected void runOneIteration() throws Exception {
        System.out.println("run in iteration..." + System.currentTimeMillis());
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(Duration.ofMillis(100), Duration.ofMillis(1000));
    }

    public static void main(String[] args) throws InterruptedException {
        AbstractScheduledServiceDemo service = new AbstractScheduledServiceDemo();
        service.startAsync();
        Thread.sleep(5000);
        service.stopAsync();
    }
}
