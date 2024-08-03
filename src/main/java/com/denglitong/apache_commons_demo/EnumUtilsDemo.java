package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.EnumUtils;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import static com.denglitong.apache_commons_demo.EnumUtilsDemo.Color.*;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/15
 */
public class EnumUtilsDemo {

    public static void main(String[] args) {
        long bits = EnumUtils.generateBitVector(Color.class, GREEN);
        System.out.println(bits); // 1

        bits = EnumUtils.generateBitVector(Color.class, GREEN, YELLOW);
        System.out.println(bits); // 3

        bits = EnumUtils.generateBitVector(Color.class, GREEN, RED);
        System.out.println(bits); // 5

        bits = EnumUtils.generateBitVector(Color.class, GREEN, PINK);
        System.out.println(bits); // 9

        System.out.println(ArrayUtils.getLength(Color.values()));

        System.out.println(EnumUtils.getEnum(Color.class, "GREEN")); // GREEN
        System.out.println(EnumUtils.getEnum(Color.class, "green")); // null
        System.out.println(EnumUtils.getEnum(Color.class, "green", RED)); // RED (default value)

        System.out.println(
                EnumUtils.getEnumIgnoreCase(Color.class, "green")
        ); // GREEN

        List<Color> colors = EnumUtils.getEnumList(Color.class);
        System.out.println(colors); // [GREEN, YELLOW, RED, PINK]

        Map<String, Color> colorMap = EnumUtils.getEnumMap(Color.class);
        System.out.println(colorMap); // {GREEN=GREEN, YELLOW=YELLOW, RED=RED, PINK=PINK}

        System.out.println(EnumUtils.isValidEnum(Color.class, "GREEN"));
        System.out.println(EnumUtils.isValidEnum(Color.class, "green")); // false
        System.out.println(EnumUtils.isValidEnumIgnoreCase(Color.class, "green")); // true

        EnumSet<Color> colorEnumSet = EnumUtils.processBitVector(Color.class, 5);
        System.out.println(colorEnumSet); // [GREEN, RED]

        // vector bits: [long1] [long2] ... [longN], 当 enum 数目少于 64 个的时候，只取最后一个 longN
        System.out.println(EnumUtils.processBitVectors(Color.class, 1)); // [GREEN]
        System.out.println(EnumUtils.processBitVectors(Color.class, 2)); // [YELLOW]
        System.out.println(EnumUtils.processBitVectors(Color.class, 1, 2)); // [YELLOW]
        System.out.println(EnumUtils.processBitVectors(Color.class, 3)); // [GREEN, YELLOW]
        System.out.println(EnumUtils.processBitVectors(Color.class, 4)); // [RED]
        System.out.println(EnumUtils.processBitVectors(Color.class, 3, 4)); // [RED]
    }

    enum Color {
        GREEN,
        YELLOW,
        RED,
        PINK;
    }
}
