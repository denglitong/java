package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.JavaVersion;

import static org.apache.commons.lang3.JavaVersion.*;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/14
 */
public class JavaVersionDemo {

    public static void main(String[] args) {
        System.out.println(JAVA_1_8);
        System.out.println(JAVA_RECENT);

        JavaVersion version = JAVA_RECENT;

        System.out.println(version.atLeast(JAVA_10));
        System.out.println(version.atMost(JAVA_1_8));
    }
}
