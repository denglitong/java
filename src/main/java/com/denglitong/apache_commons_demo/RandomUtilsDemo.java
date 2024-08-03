package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/20
 */
public class RandomUtilsDemo {

    public static void main(String[] args) {
        System.out.println(RandomUtils.nextBoolean());

        System.out.println(Arrays.toString(RandomUtils.nextBytes(0)));
        System.out.println(Arrays.toString(RandomUtils.nextBytes(1)));
        System.out.println(Arrays.toString(RandomUtils.nextBytes(2)));
        System.out.println(Arrays.toString(RandomUtils.nextBytes(3)));

        System.out.println(RandomUtils.nextInt());
        System.out.println(RandomUtils.nextInt(10, 20));

        System.out.println(RandomUtils.nextLong());
        System.out.println(RandomUtils.nextLong(100, 2000000));

        System.out.println(RandomUtils.nextDouble());
        System.out.println(RandomUtils.nextDouble(1.0, 3.0));

        System.out.println(RandomUtils.nextFloat());
        System.out.println(RandomUtils.nextFloat(2.0f, 4.0f));
    }
}
