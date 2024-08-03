package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.CharSequenceUtils;

/**
 * CharSequence -> CharBuffer, String, StringBuffer, StringBuilder
 *
 * @author litong.deng@foxmail.com
 * @date 2021/8/14
 */
public class CharSequenceUtilsDemo {

    public static void main(String[] args) {
        CharSequence cs1 = "hello, world";
        CharSequence cs2 = CharSequenceUtils.subSequence(cs1, 7);
        System.out.println(cs2);

        char[] chars = CharSequenceUtils.toCharArray(cs1);
        System.out.println(chars);
    }
}
