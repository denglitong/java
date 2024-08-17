package com.denglitong.guava_demo.strings;

import com.google.common.base.Joiner;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/7
 */
public class JoinerTest {

    public static void main(String[] args) {
        // Joiner 实例总是不可变的
        final Joiner joiner = Joiner.on(";").skipNulls();
        // Harry;Ron;Hermione
        System.out.println(joiner.join("Harry", null, "Ron", "Hermione"));

        final Joiner joiner1 = Joiner.on(";").useForNull("NULL");
        // Harry;NULL;Ron;Hermione
        System.out.println(joiner1.join("Harry", null, "Ron", "Hermione"));

    }
}
