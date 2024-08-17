package com.denglitong.guava_demo.math;

import com.google.common.math.BigIntegerMath;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;

import java.math.BigInteger;

import static java.math.RoundingMode.*;

/**
 * IntMath, LongMath, BigIntegerMath
 * <p>
 * RoundingMode:
 * DOWN: 向 0 方向舍入（去尾法）
 * UP: 向远离 0 方向舍入
 * FLOOR：向负无穷大方向舍入
 * CEILING: 向正无穷大方向舍入
 * UNNECESSARY: 不需要舍入，如果用此模式进行有舍入的运算，直接抛 ArithmeticException
 * HALE_UP: 向最近的整数舍入，其中 x.5 向远离 0 方向舍入
 * HALF_DOWN: 向最近的整数舍入，其中 x.5 向 0 方向舍入
 * HALF_EVEN: 向最近的整数舍入，其中 x.5 向相邻的偶数舍入
 *
 * @author litong.deng@foxmail.com
 * @date 2021/10/10
 */
public class MathDemo {

    public static void main(String[] args) {
        // ①
        int logFloor = LongMath.log2(100, FLOOR);
        System.out.println(logFloor); // 6
        System.out.println(LongMath.log10(100, FLOOR)); // 2

        // ② 有溢出检查的运算
        // int mustNotOverflow = IntMath.checkedAdd(Integer.MAX_VALUE, 1);
        // System.out.println(mustNotOverflow); // ArithmeticException overflow:

        int knownMultipleOfThree = 5;
        // 余数是不需要的，也就是说必须得整除，否则报错 mode was UNNECESSARY, but rounding was necessary
        // long quotient = LongMath.divide(knownMultipleOfThree, 3, UNNECESSARY);
        // System.out.println(quotient);
        knownMultipleOfThree = 6;
        long quotient = LongMath.divide(knownMultipleOfThree, 3, UNNECESSARY);
        System.out.println(quotient); // 2

        BigInteger nearestInteger = DoubleMath.roundToBigInteger(5.5, HALF_EVEN);
        System.out.println(nearestInteger); // 6

        BigInteger sideLength = BigIntegerMath.sqrt(nearestInteger, CEILING);
        System.out.println(sideLength); // 3

        System.out.println(BigIntegerMath.sqrt(BigInteger.TEN.pow(99), HALF_EVEN));

        // 最大公约数
        System.out.println(IntMath.gcd(24, 42)); // 6

        System.out.println(IntMath.isPowerOfTwo(13322)); // false

        // (1+x)^n，对 x^k 项的系数
        System.out.println(IntMath.binomial(4, 3)); // (1+x)^4 求 x^3 的系数 -> 4

        // 浮点数是否是整数
        System.out.println(DoubleMath.isMathematicalInteger(2.00)); // true
        System.out.println(DoubleMath.isMathematicalInteger(2.01)); // false
    }
}
