package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.CharUtils;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/15
 */
public class CharUtilsDemo {

    public static void main(String[] args) {
        System.out.print("--" + CharUtils.LF);
        System.out.println("--" + CharUtils.CR + "b" + "**");
        System.out.println(CharUtils.NUL);

        // ① convert string to character using first char
        System.out.println(CharUtils.toCharacterObject("BA")); // B
        System.out.println(CharUtils.toCharacterObject("c")); // c
        System.out.println(CharUtils.toCharacterObject(null)); // null

        System.out.println(CharUtils.toChar("BC")); // B
        System.out.println(CharUtils.toChar("A"));
        // System.out.println(CharUtils.toChar((String)null)); // NullPointerException

        System.out.println(CharUtils.toIntValue('0'));
        System.out.println(CharUtils.toIntValue('9'));
        // IllegalArgumentException, character must be in the range '0' - '9'
        // System.out.println(CharUtils.toIntValue('a'));

        System.out.println(CharUtils.toIntValue('a', -1)); // -1

        System.out.println(CharUtils.toString('a'));
        System.out.println(CharUtils.toString('z'));
        System.out.println(CharUtils.toString('&'));

        System.out.println(CharUtils.unicodeEscaped(' ')); // \u0020
        System.out.println(CharUtils.unicodeEscaped('A')); // \u0041

        System.out.println(CharUtils.isAscii('a'));
        System.out.println(CharUtils.isAscii('-'));
        System.out.println(CharUtils.isAscii('©')); // false
        System.out.println(CharUtils.isAscii('￥')); // false

        System.out.println(CharUtils.isAsciiAlpha('a'));
        System.out.println(CharUtils.isAsciiAlpha('-')); // false

        System.out.println(CharUtils.isAsciiAlphaUpper('A'));
        System.out.println(CharUtils.isAsciiAlphaUpper('a')); // false

        System.out.println(CharUtils.isAsciiPrintable('a'));
        System.out.println(CharUtils.isAsciiPrintable('-'));
        System.out.println(CharUtils.isAsciiPrintable('©')); // false
        System.out.println(CharUtils.isAsciiPrintable('\n')); // false

        System.out.println(CharUtils.isAsciiNumeric('0'));
        System.out.println(CharUtils.isAsciiNumeric('9'));
        System.out.println(CharUtils.isAsciiNumeric('a')); // false

        // [a-zA-Z0-9]
        System.out.println(CharUtils.isAsciiAlphanumeric('a')); // true
        System.out.println(CharUtils.isAsciiAlphanumeric('z')); // true
        System.out.println(CharUtils.isAsciiAlphanumeric('A')); // true
        System.out.println(CharUtils.isAsciiAlphanumeric('Z')); // true
        System.out.println(CharUtils.isAsciiAlphanumeric('0')); // true
        System.out.println(CharUtils.isAsciiAlphanumeric('9')); // true
        System.out.println(CharUtils.isAsciiAlphanumeric('-')); // false
        System.out.println(CharUtils.isAsciiAlphanumeric('_')); // false

        System.out.println(CharUtils.compare('a', 'c')); // -2
        System.out.println(CharUtils.compare('c', 'a')); // 2
        System.out.println(CharUtils.compare('a', 'A')); // 32
    }
}
