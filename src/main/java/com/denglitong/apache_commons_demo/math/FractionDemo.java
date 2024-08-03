package com.denglitong.apache_commons_demo.math;

import org.apache.commons.lang3.math.Fraction;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/26
 */
public class FractionDemo {

    public static void main(String[] args) {
        // 分数 1/3
        Fraction fraction = Fraction.getFraction("1/3");
        System.out.println(fraction); // 1/3
        System.out.println(fraction.getNumerator()); // 分子 1
        System.out.println(fraction.getDenominator()); // 分母 3

        // 3.14 -> 157/50
        fraction = Fraction.getFraction(3.14);
        System.out.println(fraction.getNumerator()); // 157
        System.out.println(fraction.getDenominator()); // 50

        // 3 + 2/3 -> 11/3
        fraction = Fraction.getFraction(3, 2, 3);
        System.out.println(fraction); // 11/3
        System.out.println(fraction.getNumerator()); // 11
        System.out.println(fraction.getDenominator()); // 3

        // 2/6 -> 1/3
        fraction = Fraction.getReducedFraction(2, 6);
        System.out.println(fraction.getNumerator()); // 1
        System.out.println(fraction.getDenominator()); // 3

        // -1 3/4 -> -7/4
        fraction = Fraction.getFraction(-1, 3, 4);
        // 返回小数部分（永远为正值）
        System.out.println(fraction.getProperNumerator()); // 3
        // 返回整数部分
        System.out.println(fraction.getProperWhole()); // -1

        System.out.println(fraction.intValue()); // -1
        System.out.println(fraction.doubleValue()); // -1.75

        fraction = Fraction.getFraction(4, 12);
        System.out.println(fraction); // 4/12
        System.out.println(fraction.reduce()); // 1/3

        fraction = Fraction.getFraction(3, 4);
        System.out.println(fraction); // 3/4
        System.out.println(fraction.doubleValue()); // 0.75
        System.out.println(fraction.invert()); // 4/3
        System.out.println(fraction.invert().doubleValue()); // 1.3333333333333333
        System.out.println(fraction.negate()); // -3/4
        System.out.println(fraction.negate().abs()); // 3/4

        fraction = fraction.pow(2);
        System.out.println(fraction); // 9/16

        Fraction fraction1 = Fraction.getFraction(3, 4);
        Fraction fraction2 = Fraction.getFraction(-1, 1, 4);
        System.out.println(fraction1.add(fraction2)); // -1/2
        System.out.println(fraction1.subtract(fraction2)); // 2/1
        System.out.println(fraction1.multiplyBy(fraction2)); // -15/16
        System.out.println(fraction1.divideBy(fraction2)); // -3/5
        System.out.println(fraction1.equals(fraction2)); // false
        System.out.println(fraction1.compareTo(fraction2)); // 1
        System.out.println(fraction2.toProperString()); // -1 1/4

        System.out.println(Fraction.ZERO);
        System.out.println(Fraction.ONE);
        System.out.println(Fraction.ONE_HALF);
        System.out.println(Fraction.ONE_THIRD);
        System.out.println(Fraction.ONE_QUARTER);
        System.out.println(Fraction.ONE_FIFTH);
        System.out.println(Fraction.TWO_THIRDS);
        System.out.println(Fraction.TWO_THIRDS);
        System.out.println(Fraction.TWO_QUARTERS);
        System.out.println(Fraction.TWO_FIFTHS);
        System.out.println(Fraction.THREE_QUARTERS);
        System.out.println(Fraction.THREE_FIFTHS);
        System.out.println(Fraction.FOUR_FIFTHS);
    }
}
