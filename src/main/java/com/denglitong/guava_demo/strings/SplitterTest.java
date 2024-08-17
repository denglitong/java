package com.denglitong.guava_demo.strings;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/7
 */
public class SplitterTest {

    public static void main(String[] args) {
        // [, a, , b] 尾部的空字符被忽略了
        System.out.println(Arrays.toString(",a,,b,".split(",")));

        // Splitter 实例总是不可变的
        final Splitter splitter = Splitter.on(",")
                .trimResults()
                .omitEmptyStrings();
        List<String> strings = Lists.newArrayList(splitter.split(", a,,b ,"));
        System.out.println(strings); // [a, b]

    }
}
