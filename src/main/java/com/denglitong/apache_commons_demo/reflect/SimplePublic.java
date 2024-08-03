package com.denglitong.apache_commons_demo.reflect;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/26
 */
public class SimplePublic {
    private int number;
    private String name;
    private int age;

    public SimplePublic(int number, String name, int age) {
        this.number = number;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
