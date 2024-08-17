package com.denglitong.guava_demo.strings;

import com.google.common.base.Charsets;

import java.util.Arrays;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/7
 */
public class CharsetsDemo {

    public static void main(String[] args) {
        String strings = "hello world";

        // @Deprecated
        /*try {
            byte[] bytes = strings.getBytes("UTF-8")
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }*/

        byte[] bytes = strings.getBytes(Charsets.UTF_8);
        System.out.println(Arrays.toString(bytes));
    }
}
