package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.CharSetUtils;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/15
 */
public class CharsetDemo {

    public static void main(String[] args) {
        System.out.println(CharSet.ASCII_ALPHA);
        System.out.println(CharSet.ASCII_ALPHA_LOWER);
        System.out.println(CharSet.ASCII_ALPHA_UPPER);
        System.out.println(CharSet.ASCII_NUMERIC);
        System.out.println(CharSet.EMPTY);

        CharSet charSet = CharSet.getInstance("hello, world");
        System.out.println(charSet.toString());
        System.out.println(charSet.contains('w'));
        System.out.println(charSet.contains('L'));
        System.out.println(charSet.equals(CharSet.getInstance("world, hello"))); // true

        System.out.println(CharSetUtils.containsAny("hello", "a-e")); // true
        System.out.println(CharSetUtils.count("hello", "a-e")); // 1
        System.out.println(CharSetUtils.count("hllo", "a-e")); // 0
        System.out.println(CharSetUtils.count("hello", "l-n")); // 2
        System.out.println(CharSetUtils.count("hello", "l-o")); // 3

        String helloWorld = "hello,world";
        String withOutWorld = CharSetUtils.delete(helloWorld, "world");
        System.out.println(withOutWorld); // he,

        String keepWorld = CharSetUtils.keep(helloWorld, "world");
        System.out.println(keepWorld); // lloworld

        // 去掉包含在集合内的重复字符
        String hello = "hello";
        System.out.println(CharSetUtils.squeeze(hello, "l")); // helo
        System.out.println(CharSetUtils.squeeze(hello, "heo")); // hello
        System.out.println(CharSetUtils.squeeze("aabbbdddd", "a-c")); // abcdddd

    }
}
