package com.denglitong.guava_demo.strings;

import com.google.common.base.CharMatcher;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/7
 */
public class CharMatcherTest {

    public static void main(String[] args) {
        String noControl = CharMatcher.javaIsoControl().removeFrom("0XFF");
        System.out.println(noControl);

        String theDigits = CharMatcher.digit().retainFrom("h1e3l2w4i5s9dgg8r5t");
        System.out.println(theDigits); // 13245985
        theDigits = CharMatcher.inRange('0', '9').retainFrom("h1e3l2w4i5s9dgg8r5t");
        System.out.println(theDigits); // 13245985

        String spaced = CharMatcher.whitespace().trimAndCollapseFrom(" a  b   ", ' ');
        System.out.println(spaced); // "a b"

        String lowerAndDigit = CharMatcher.inRange('0', '9')
                .or(CharMatcher.javaLowerCase())
                .retainFrom("HELdal12HIWas_——");
        System.out.println(lowerAndDigit); // dal12as

        CharMatcher digit = CharMatcher.inRange('0', '9');
        CharMatcher letter = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z'));
        CharMatcher letterOrDigit = letter.or(digit);

        CharMatcher vowel = CharMatcher.anyOf("aeiou");
        
    }
}
