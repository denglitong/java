package com.denglitong.guava_demo.collection;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;

import static com.denglitong.guava_demo.collection.BiMapDemo.Color.*;
import static com.denglitong.guava_demo.collection.BiMapDemo.Store.*;

/**
 * 键值对的双重映射
 *
 * @author litong.deng@foxmail.com
 * @date 2021/9/1
 */
public class BiMapDemo {

    public static void main(String[] args) {
        BiMap<String, Double> coins = HashBiMap.create();
        coins.put("一分", 0.01);
        coins.put("两分", 0.02);
        coins.put("五分", 0.05);
        coins.put("一角", 0.1);
        coins.put("两角", 0.2);
        coins.put("五角", 0.5);
        coins.put("一元", 1.0);
        // {一分=0.01, 两分=0.02, 五分=0.05, 一角=0.1, 两角=0.2, 五角=0.5, 一元=1.0}
        System.out.println(coins);

        // coins.put("一元", 0.1); // IllegalArgumentException: value already present: 0.1
        // coins.forcePut("一元", 0.1); // ok
        // {一分=0.01, 两分=0.02, 五分=0.05, 两角=0.2, 五角=0.5, 一元=0.1}
        // System.out.println(coins);

        BiMap<Double, String> coinsInverse = coins.inverse();
        // {0.01=一分, 0.02=两分, 0.05=五分, 0.1=一角, 0.2=两角, 0.5=五角, 1.0=一元}
        System.out.println(coinsInverse);
        System.out.println(coinsInverse.get(0.01));

        coinsInverse.put(0.1, "一毛");
        // {0.01=一分, 0.02=两分, 0.05=五分, 0.2=两角, 0.5=五角, 1.0=一元, 0.1=一毛}
        System.out.println(coinsInverse);
        // {一分=0.01, 两分=0.02, 五分=0.05, 两角=0.2, 五角=0.5, 一元=1.0, 一毛=0.1}
        System.out.println(coins);

        EnumBiMap<Color, Store> enumBiMap = EnumBiMap.create(Color.class, Store.class);
        enumBiMap.put(RED, LOW);
        enumBiMap.put(ORANGE, MEDIUM);
        enumBiMap.put(PINK, HIGH);
        // {RED=LOW, ORANGE=MIDIUM, PINK=HIGH}
        System.out.println(enumBiMap);
        // {LOW=RED, MIDIUM=ORANGE, HIGH=PINK}
        System.out.println(enumBiMap.inverse());

        EnumHashBiMap<Color, Store> enumHashBiMap = EnumHashBiMap.create(Color.class);
        enumHashBiMap.put(RED, LOW);
        // {LOW=RED}
        System.out.println(enumHashBiMap.inverse());
    }

    public static enum Color {
        RED,
        ORANGE,
        PINK;
    }

    public static enum Store {
        LOW,
        MEDIUM,
        HIGH;
    }
}
