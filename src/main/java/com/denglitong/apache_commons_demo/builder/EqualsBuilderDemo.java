package com.denglitong.apache_commons_demo.builder;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/24
 */
public class EqualsBuilderDemo {

    public static void main(String[] args) {
        EqualsBuilder builder = new EqualsBuilder();
        CompareToBuilderDemo.Boy boy = new CompareToBuilderDemo.Boy(1, "leon", 18, "basketball,footbal");
        CompareToBuilderDemo.Boy boy1 = new CompareToBuilderDemo.Boy(1, "leon", 18, "singing,running");
        builder.reflectionAppend(boy, boy1);
        System.out.println(builder.build()); // true
        System.out.println(builder.isEquals()); // true

        builder.append(10L, 20L);
        System.out.println(builder.build()); // false
        System.out.println(builder.isEquals()); // false
    }
}
