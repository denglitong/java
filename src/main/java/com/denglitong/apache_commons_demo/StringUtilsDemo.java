package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/21
 */
public class StringUtilsDemo {

    public static void main(String[] args) {
        // ① 字符串缩写
        String line = "Now is time for all good men";
        System.out.println(StringUtils.abbreviate(line, 10)); // Now is ...
        System.out.println(StringUtils.abbreviate(line, "***", 10)); // Now is ***
        System.out.println(StringUtils.abbreviate(line, "*", 10)); // Now is ti*
        System.out.println(StringUtils.abbreviateMiddle(line, ".", 10)); // Now i. men
        System.out.println(StringUtils.abbreviateMiddle(line, "**", 10)); // Now ** men

        System.out.println(StringUtils.abbreviate("abcdefghijklmno", 4, 10)); // abcdefg...
        System.out.println(StringUtils.abbreviate("abcdefghijklmno", 5, 10)); // ...fghi...

        // ② 增加后缀
        String fileName = "/usr/local/share/index.html";
        System.out.println(StringUtils.appendIfMissing(
                fileName, (CharSequence)".html", (CharSequence)null));

        fileName = "/usr/local/share/index";
        System.out.println(StringUtils.appendIfMissing(
                fileName, (CharSequence)".html", (CharSequence)null)); // // /usr/local/share/index.html

        fileName = "/usr/local/share/index.";
        System.out.println(StringUtils.appendIfMissing(
                fileName, (CharSequence)".html", (CharSequence)null)); // /usr/local/share/index..html

        fileName = "/usr/local/share/index.htm";
        System.out.println(StringUtils.appendIfMissing(
                fileName, (CharSequence)".html", (CharSequence)".htm")); // /usr/local/share/index.htm

        fileName = "/usr/local/share/index";
        System.out.println(StringUtils.appendIfMissing(
                fileName, (CharSequence)".html", (CharSequence)".htm")); // /usr/local/share/index.html

        fileName = "/usr/local/share/index.HTML";
        System.out.println(StringUtils.appendIfMissingIgnoreCase(
                fileName, (CharSequence)".html", (CharSequence)".htm")); // /usr/local/share/index.HTML

        // ③
        System.out.println(StringUtils.capitalize("hello")); // Hello
        System.out.println(StringUtils.capitalize("HELLO")); // HELLO

        // ④ 居中返回，以指定的空格数包围
        System.out.println("[" + StringUtils.center("a", -1) + "]"); // [a]
        System.out.println("[" + StringUtils.center("a", 0) + "]"); // [a]
        System.out.println("[" + StringUtils.center("a", 1) + "]"); // [a]
        System.out.println("[" + StringUtils.center("a", 2) + "]"); // [a ]
        System.out.println("[" + StringUtils.center("a", 3) + "]"); // [ a ]
        System.out.println("[" + StringUtils.center("a", 4) + "]"); // [ a  ]
        System.out.println("[" + StringUtils.center("a", 5) + "]"); // [  a  ]
        // 以指定的占位符居中返回
        System.out.println("[" + StringUtils.center("a", 4, '*') + "]"); // [*a**]
        System.out.println("[" + StringUtils.center("a", 5, '*') + "]"); // [**a**]
        System.out.println("[" + StringUtils.center("a", 4, "_-") + "]"); // [_a_-]
        System.out.println("[" + StringUtils.center("a", 5, "_-") + "]"); // [_-a_-]

        // ⑤ 移除尾部换行符 \r, \n, \r\n
        line += "\r";
        String lineSeparator = System.getProperty("line.separator");
        // \r 回车符输出后输出内容置为空，这一行重新输出，接后面的 "---" + lineSeperator
        System.out.print(line + "---" + lineSeparator); // ---
        System.out.print(StringUtils.chomp(line) + "---" + lineSeparator); // Now is time for all good men---

        line = "Now is time for all good men" + "\n";
        System.out.println(StringUtils.chomp(line)); // Now is time for all good men

        // ⑥ 移除尾部字符，如果尾部字符为 \r\n 则都移除
        System.out.println("[" + StringUtils.chop("abcd") + "]"); // [abc]
        System.out.println("[" + StringUtils.chop("abcd ") + "]"); // [abcd]
        System.out.println("[" + StringUtils.chop("abcd\r") + "]"); // [abcd]
        System.out.println("[" + StringUtils.chop("abcd\n") + "]"); // [abcd]
        System.out.println("[" + StringUtils.chop("abcd\n\r") + "]"); // [abcd\n]
        System.out.println("[" + StringUtils.chop("abcd\r\n") + "]"); // [abcd]

        // ⑦ 比较大小
        System.out.println(StringUtils.compare("a", "A")); // 32
        System.out.println("a".compareTo("A")); // 32
        System.out.println(StringUtils.compareIgnoreCase("a", "A")); // 0

        // null 值是否为小值、大值
        System.out.println(StringUtils.compare(null, "a", true)); // -1
        System.out.println(StringUtils.compare(null, "a", false)); // 1
        System.out.println(StringUtils.compareIgnoreCase(null, "a", true)); // -1
        System.out.println(StringUtils.compareIgnoreCase(null, "a", false)); // 1

        // ⑧ 是否包含
        System.out.println(StringUtils.contains("hello", "he")); // true
        System.out.println(StringUtils.contains("hello", "lle")); // false

        System.out.println(StringUtils.contains("bcd", "a")); // false
        System.out.println(StringUtils.contains("bcd", "b")); // true
        System.out.println(StringUtils.contains("bcd", "e")); // false
        System.out.println(StringUtils.contains("bcd", 97)); // false
        System.out.println(StringUtils.contains("bcd", 100)); // true
        System.out.println((int)'a' + " " + (int)'d'); // 97 100

        System.out.println(StringUtils.containsAny("hello", 'a', 'b', 'c')); // false
        System.out.println(StringUtils.containsAny("hello", 'a', 'b', 'e')); // true

        System.out.println(StringUtils.containsNone("hello", 'a', 'b', 'c')); // true
        System.out.println(StringUtils.containsNone("hello", 'a', 'b', 'e')); // false

        System.out.println(StringUtils.containsOnly("abcabcabcdddd", 'a', 'b', 'c')); // false
        System.out.println(StringUtils.containsOnly("abcabcabcdddd", 'a', 'b', 'c', 'd')); // true
        System.out.println(StringUtils.containsOnly("abcabcabcdddd", 'a', 'b', 'c', 'd', 'e')); // true

        System.out.println(StringUtils.containsOnly("abcabcabcdddd", "abc")); // false
        System.out.println(StringUtils.containsOnly("abcabcabcdddd", "abcd")); // true
        System.out.println(StringUtils.containsOnly("abcabcabcdddd", "abcde")); // true

        System.out.println(StringUtils.containsWhitespace("abc")); // false
        System.out.println(StringUtils.containsWhitespace("ab c")); // true
        System.out.println(StringUtils.containsWhitespace("abc  ")); // true

        // ⑦ 匹配数
        System.out.println(StringUtils.countMatches("hello", 'l')); // 2
        System.out.println(StringUtils.countMatches("hello", 'h')); // 1
        System.out.println(StringUtils.countMatches("hello", 'j')); // 0
        System.out.println(StringUtils.countMatches("hello", "he")); // 1

        // ⑧ 默认空值处理
        System.out.println(StringUtils.defaultIfBlank("abc", "**")); // abc
        System.out.println(StringUtils.defaultIfBlank("", "**")); // **
        System.out.println(StringUtils.defaultIfBlank("   ", "**")); // **

        System.out.println(StringUtils.defaultIfEmpty("", "**")); // **
        System.out.println(StringUtils.defaultIfEmpty("   ", "**")); // "   "

        // java.lang.String@9807454[hash=0,value={}]
        System.out.println(ToStringBuilder.reflectionToString(StringUtils.defaultString(null)));
        // java.lang.String@9807454[hash=0,value={}]
        System.out.println(ToStringBuilder.reflectionToString(StringUtils.defaultString("")));
        // java.lang.String@378fd1ac[hash=97,value={a}]
        System.out.println(ToStringBuilder.reflectionToString(StringUtils.defaultString("a")));

        System.out.println(StringUtils.defaultString(null, "DEFAULT")); // DEFAULT
        System.out.println(StringUtils.defaultString("", "DEFAULT")); // ""

        System.out.println(StringUtils.deleteWhitespace(" a  b c  ")); // abc

        // the portion of str2 where it differs from str1
        System.out.println(StringUtils.difference("hello", "world")); // world
        System.out.println(StringUtils.difference("i am a human", "i am a robot")); // robot

        System.out.println(StringUtils.endsWith("index.html", ".html")); // true
        System.out.println(StringUtils.endsWith("index.html", ".htm")); // fase
        System.out.println(StringUtils.endsWithAny("index.html", ".htm", ".html")); // true

        System.out.println(StringUtils.endsWith("index.html", ".HTML")); // false
        System.out.println(StringUtils.endsWithIgnoreCase("index.html", ".HTML")); // true

        System.out.println(StringUtils.equals("hello", "hello")); // true
        System.out.println(StringUtils.equalsIgnoreCase("hello", "HELLO")); // true
        System.out.println(StringUtils.equalsAny("hello", "world", "hello")); // true
        System.out.println(StringUtils.equalsAnyIgnoreCase("hello", "world", "HELLO")); // true

        String[] strings = new String[]{null, "", " ", "hello", "world"};
        System.out.println(StringUtils.firstNonBlank(strings)); // hello
        System.out.println(StringUtils.firstNonEmpty(strings)); // ""

        byte[] bytes = "hello".getBytes(Charset.defaultCharset());
        System.out.println(Arrays.toString(bytes));
        bytes = "hello".getBytes(Charset.forName("UTF-8"));
        System.out.println(Arrays.toString(bytes));
        bytes = "hello".getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(bytes));

        strings = new String[]{"abcd", "abc", "d", "ab"};
        System.out.println(StringUtils.getCommonPrefix(strings)); // ""
        strings = new String[]{"abcd", "abc", "ab"};
        System.out.println(StringUtils.getCommonPrefix(strings)); // ab

        String digit = StringUtils.getDigits("120~456");
        System.out.println(digit); // "120456"
        System.out.println(StringUtils.getDigits("#%^234567*&^789")); // "234567789"

        System.out.println(StringUtils.join(strings, ",")); // abcd,abc,ab
        System.out.println(StringUtils.joinWith(",", strings)); // abcd,abc,ab

        System.out.println(StringUtils.substring("hello", 0, 10));

        System.out.println(StringUtils.leftPad("bat", 3)); // "bat"
        System.out.println(StringUtils.leftPad("bat", 5)); // "  bat"
        System.out.println(StringUtils.leftPad("bat", 5, "*")); // "**bat"

        System.out.println(StringUtils.mid("hello", 2, 2)); // "ll"

        System.out.println(StringUtils.normalizeSpace("  hello  world ")); // "hello world"

        System.out.println(StringUtils.overlay("abcdefgh", "ABC", 1, 4)); // aABCefgh
        System.out.println(StringUtils.overlay("abcdefgh", "ABC", 1, 6)); // aABCgh

        System.out.println(StringUtils.rotate("ABCDEF", 1)); // FABCDE
        System.out.println(StringUtils.rotate("ABCDEF", 2)); // EFABCD

        System.out.println(StringUtils.rotate("ABCDEF", -1)); // BCDEFA
        System.out.println(StringUtils.rotate("ABCDEF", -2)); // CDEFAB

        System.out.println(Arrays.toString(
                StringUtils.split("ab,c,de,fgh", ","))); // [ab, c, de, fgh]

        System.out.println(StringUtils.strip(" ab c   ")); // "ab c"

        System.out.println(StringUtils.swapCase("aBcD")); // AbCd

        System.out.println(StringUtils.trim(null)); // null
        System.out.println(StringUtils.trimToEmpty("  ")); // ""

        System.out.println(StringUtils.unwrap("*hello*", "*")); // hello
        System.out.println(StringUtils.unwrap("*hello", "*")); // *hello
        System.out.println(StringUtils.unwrap("hello*", "*")); // hello*

        char[] chars = new char[]{104, 101, 108, 108, 111};
        System.out.println(StringUtils.valueOf(chars)); // hello

        System.out.println(StringUtils.wrapIfMissing("hello", "*")); // *hello*
        System.out.println(StringUtils.wrapIfMissing("*hello", "*")); // *hello*
        System.out.println(StringUtils.wrapIfMissing("hello*", "*")); // *hello*

        System.out.println(StringUtils.SPACE);
        System.out.println(StringUtils.EMPTY);
        System.out.println(StringUtils.LF);
        System.out.println(StringUtils.CR);
        System.out.println(StringUtils.INDEX_NOT_FOUND);
    }
}
