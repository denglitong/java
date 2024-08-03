package com.denglitong.apache_commons_demo.math;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/25
 */
public class NumberUtilsDemo {

    public static void main(String[] args) {
        System.out.println(NumberUtils.toInt("12")); // 12
        System.out.println(NumberUtils.toInt("12ab3")); // 0
        System.out.println(NumberUtils.toInt(null)); // 0
        System.out.println(NumberUtils.toInt("")); // 0

        System.out.println(NumberUtils.toInt("12", -1)); // 12
        System.out.println(NumberUtils.toInt("12ab3", -1)); // -1

        // 保留 2 位小数，默认四舍五入
        BigDecimal bigDecimal = NumberUtils.toScaledBigDecimal("3.1459215");
        System.out.println(bigDecimal.doubleValue()); // 3.15

        BigDecimal bigDecimal1 = NumberUtils.toScaledBigDecimal("3.1459215", 2, RoundingMode.DOWN);
        System.out.println(bigDecimal1.doubleValue()); // 3.14

        BigDecimal bigDecimal2 = NumberUtils.toScaledBigDecimal("3.1459215", 3, RoundingMode.HALF_DOWN);
        System.out.println(bigDecimal2.doubleValue()); // 3.146

        Number number = NumberUtils.createNumber("12.56");
        System.out.println(number); // 12.56
        // NumberFormatException: 12.56abc is not a valid number.
        // number = NumberUtils.createNumber("12.56abc");
        // NumberFormatException: " 12.56 " is not a valid number.
        // number = NumberUtils.createNumber(" 12.56 ");

        BigInteger bigInteger = NumberUtils.createBigInteger("0xFFFFFFFFFFFFFF");
        System.out.println(bigInteger); // 72057594037927935
        BigDecimal bigDecimal3 = NumberUtils.createBigDecimal("12345678909876543212345678900987654321");
        System.out.println(bigDecimal3);

        System.out.println(NumberUtils.min(1, 2, 3)); // 1
        System.out.println(NumberUtils.min(new double[]{3.14, 3.0, 12.45})); // 3.0

        System.out.println(NumberUtils.isDigits(" ")); // false
        System.out.println(NumberUtils.isDigits(" 12")); // false
        System.out.println(NumberUtils.isDigits(" 1 2")); // false
        System.out.println(NumberUtils.isDigits("12")); // true

        System.out.println(NumberUtils.isCreatable("123L")); // true
        System.out.println(NumberUtils.isCreatable("1.23e-34")); // true
        System.out.println(NumberUtils.isCreatable("0x1234")); // true
        System.out.println(NumberUtils.isCreatable("0765")); // true
        System.out.println(NumberUtils.isCreatable("123")); // true
        System.out.println(NumberUtils.isCreatable("123abc")); // false

        System.out.println(NumberUtils.isParsable(" 123 ")); // false

        System.out.println(NumberUtils.compare(1, 2));

        System.out.println(NumberUtils.LONG_ZERO); // 0
        System.out.println(NumberUtils.LONG_ONE); // 1
        System.out.println(NumberUtils.LONG_MINUS_ONE); // -1
        System.out.println(NumberUtils.LONG_INT_MAX_VALUE); // 2147483647
        System.out.println(NumberUtils.LONG_INT_MIN_VALUE); // -2147483648
    }
}
