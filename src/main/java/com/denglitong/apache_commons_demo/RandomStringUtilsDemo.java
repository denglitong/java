package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/20
 */
public class RandomStringUtilsDemo {

    public static void main(String[] args) {
        int minLength = 6, maxLength = 12;
        System.out.println(RandomStringUtils.random(minLength));
        System.out.println(RandomStringUtils.randomAscii(minLength));
        System.out.println(RandomStringUtils.randomAscii(minLength, maxLength));

        // [a-zA-Z]
        System.out.println(RandomStringUtils.randomAlphabetic(minLength));
        System.out.println(RandomStringUtils.randomAlphabetic(minLength, maxLength));

        // [a-zA-Z0-9]
        System.out.println(RandomStringUtils.randomAlphanumeric(minLength));
        System.out.println(RandomStringUtils.randomAlphanumeric(minLength, maxLength));

        // POSIX [:graph:] only match visible characters, does not match NUL, Backspace, BEL...
        System.out.println(RandomStringUtils.randomGraph(minLength));
        System.out.println(RandomStringUtils.randomGraph(minLength, maxLength));

        // [0-9]
        System.out.println(RandomStringUtils.randomNumeric(minLength));
        System.out.println(RandomStringUtils.randomNumeric(minLength, maxLength));

        // POSIX [:print:] all visible ASCII character and spaces (except control characters)
        System.out.println(RandomStringUtils.randomPrint(minLength));
        System.out.println(RandomStringUtils.randomPrint(minLength, maxLength));

        // generate strings whether include letters or numbers
        System.out.println(RandomStringUtils.random(minLength, false, false));
        System.out.println(RandomStringUtils.random(minLength, true, false));
        System.out.println(RandomStringUtils.random(minLength, false, true));
        System.out.println(RandomStringUtils.random(minLength, true, true));

        System.out.println(RandomStringUtils.random(minLength, 48, 100, true, true));

        char[] chars = new char[]{
                'a', 'b', 'c', 'd', 'e',
                'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o'
        };
        System.out.println(RandomStringUtils.random(minLength, 0, 5, true, true, chars));
        System.out.println(RandomStringUtils.random(minLength, 5, 10, true, true, chars));

        System.out.println(RandomStringUtils.random(minLength, "abc123"));
        System.out.println(RandomStringUtils.random(minLength, (String)null));
        // System.out.println(RandomStringUtils.random(minLength, "")); // IllegalArgumentException

        System.out.println(RandomStringUtils.random(minLength, chars));
        System.out.println(RandomStringUtils.random(minLength, 'a', 'b', 'c', '4', '5'));
    }
}
