package com.denglitong.guava_demo.concurrent;

import com.google.common.util.concurrent.Service;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/5
 */
public class ServiceDemo {

    public static void main(String[] args) throws Exception {
        System.out.println(Service.State.NEW);
        System.out.println(Service.State.STARTING);
        System.out.println(Service.State.RUNNING);
        System.out.println(Service.State.STOPPING);
        System.out.println(Service.State.TERMINATED);
        System.out.println(Service.State.FAILED);
    }
}
