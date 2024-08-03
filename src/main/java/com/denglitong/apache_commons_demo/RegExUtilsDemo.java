package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.RegExUtils;

import java.util.regex.Pattern;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/21
 */
public class RegExUtilsDemo {

    public static void main(String[] args) {
        System.out.println(RegExUtils.removeAll("hello, world", "lo")); // hel, world
        System.out.println(RegExUtils.removeAll("hello, world", Pattern.compile("lo"))); // hel, world
        System.out.println(RegExUtils.removeAll("hello, world", Pattern.compile("[lo]"))); // he, wrd

        // All , static methods
        System.out.println(RegExUtils.removeFirst("All methods, static methods", "methods"));
        // All , static methods
        System.out.println(RegExUtils.removeFirst("All methods, static methods", Pattern.compile("methods")));

        System.out.println(RegExUtils.removePattern("hello, world", "[lo]")); // he, wrd

        // helLO, world
        System.out.println(RegExUtils.replaceAll("hello, world", "lo", "LO"));
        // All METHODS, static METHODS
        System.out.println(RegExUtils.replaceAll(
                "All methods, static methods", Pattern.compile("methods"), "METHODS"));

        // All Methods, static methods
        System.out.println(RegExUtils.replaceFirst(
                "All methods, static methods", Pattern.compile("methods"), "Methods"));
        // Sometimes i cry, somewhere inside
        System.out.println(RegExUtils.replaceFirst(
                "sometimes i cry, somewhere inside", "some", "Some"));

        // Sometimes i cry, Somewhere inside
        System.out.println(RegExUtils.replacePattern(
                "sometimes i cry, somewhere inside", "some", "Some"));
    }
}
