package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

/**
 * 大端字节序：高位字节在前，低位字节在后，这是人类读写数字的习惯方法
 * 小端字节序：低位字节在前，高位字节在后
 *
 * @author litong.deng@foxmail.com
 * @date 2021/8/15
 */
public class ConversionDemo {

    public static void main(String[] args) {
        System.out.println(Conversion.hexDigitToInt('0'));
        System.out.println(Conversion.hexDigitToInt('9'));
        System.out.println(Conversion.hexDigitToInt('A')); // 10
        System.out.println(Conversion.hexDigitToInt('F')); // 15
        System.out.println(Conversion.hexDigitToInt('f')); // 15
        // System.out.println(Conversion.hexDigitToInt('G')); // IllegalArgumentException

        // [1000] -> 2^3
        System.out.println(Conversion.hexDigitMsb0ToInt('1')); // 8
        // [0100] -> 2^2
        System.out.println(Conversion.hexDigitMsb0ToInt('2')); // 4

        // [true, false, false, false] [1, 0, 0, 0] 默认小端序
        System.out.println(Arrays.toString(Conversion.hexDigitToBinary('1')));
        // [false, true, false, false] [0, 1, 0, 0]
        System.out.println(Arrays.toString(Conversion.hexDigitToBinary('2')));

        // [false, false, false, true] [0, 0, 0, 1] 大端序，低位字节在后
        System.out.println(Arrays.toString(Conversion.hexDigitMsb0ToBinary('1')));
        // [false, false, true, false] [0, 0, 1, 0]
        System.out.println(Arrays.toString(Conversion.hexDigitMsb0ToBinary('2')));

        boolean[] bits = new boolean[]{true, false, true, false};
        System.out.println(Conversion.binaryToHexDigit(bits)); // 5
        System.out.println(Conversion.binaryBeMsb0ToHexDigit(bits)); // a(10)
        // 2，默认小端序，srcPos 参数左起 [false, true, false] -> 2
        System.out.println(Conversion.binaryToHexDigit(bits, 1));
        // 5，大端序，srcPos 参数右起 【true, false, true】 -> 5
        System.out.println(Conversion.binaryBeMsb0ToHexDigit(bits, 1));

        System.out.println(Conversion.intToHexDigit(0));
        System.out.println(Conversion.intToHexDigit(9));
        System.out.println(Conversion.intToHexDigit(10)); // a
        System.out.println(Conversion.intToHexDigit(15)); // f

        System.out.println(Conversion.intToHexDigitMsb0(0)); // 0
        System.out.println(Conversion.intToHexDigitMsb0(9)); // 9
        System.out.println(Conversion.intToHexDigitMsb0(10)); // 5 10[1010] -> 5[0101]
        System.out.println(Conversion.intToHexDigitMsb0(15)); // f
    }
}
