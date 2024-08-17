package com.denglitong.guava_demo.concurrent;

import com.google.common.util.concurrent.AbstractIdleService;

/**
 * AbstractIdleService 提供的抽象是，服务在 running 状态时不会执行任何动作
 * 因此在 running 时也不需要启动线程 - 但需要处理开启/开闭动作
 * <p>
 * 可以理解为一个逻辑上的 service，没有承载具体的工作负荷（即 running()）
 *
 * @author litong.deng@foxmail.com
 * @date 2021/10/5
 */
public class AbstractIdleServiceDemo extends AbstractIdleService {
    @Override
    protected void startUp() throws Exception {
        System.out.println("do something on startUp");
    }

    @Override
    protected void shutDown() throws Exception {
        System.out.println("do something on shutDown");
    }

    public static void main(String[] args) throws Exception {
        AbstractIdleServiceDemo abstractIdleServiceDemo = new AbstractIdleServiceDemo();
        System.out.println(abstractIdleServiceDemo.state()); // NEW
        abstractIdleServiceDemo.startAsync();
        System.out.println(abstractIdleServiceDemo.state()); // STARTING
        Thread.sleep(1000);
        System.out.println(abstractIdleServiceDemo.state()); // RUNNING

        abstractIdleServiceDemo.shutDown();
        System.out.println(abstractIdleServiceDemo.state()); // RUNNING
        Thread.sleep(1000);
        System.out.println(abstractIdleServiceDemo.state()); // RUNNING

        abstractIdleServiceDemo.stopAsync();
        System.out.println(abstractIdleServiceDemo.state()); // STOPPING
        Thread.sleep(1000);
        System.out.println(abstractIdleServiceDemo.state()); // TERMINATED
    }
}
