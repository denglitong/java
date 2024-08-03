package com.denglitong.apache_commons_demo.tuple;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/27
 */
public class PairDemo {

    public static void main(String[] args) {
        Pair<String, Integer> pair = Pair.of("leon", 18);
        System.out.println(pair.getLeft() + " " + pair.getRight()); // leon 18
        System.out.println(pair.getKey() + ":" + pair.getValue());  // leon:18

        // pair.setValue(17); // UnsupportedOperationException: ImmutablePair.setValue

        MutablePair<String, String> pair1 = MutablePair.of("siri", "rain bow");
        System.out.println(pair1.getLeft() + ":" + pair1.getRight()); // siri:rain bow
        pair1.setLeft("hello");
        pair1.setRight("world");
        System.out.println(pair1.getKey() + ":" + pair1.getValue()); // hello:world

        Triple<Integer, Integer, Integer> triple = Triple.of(1, 2, 3);
        System.out.println(triple.getLeft() + ":" + triple.getMiddle() + ":" + triple.getRight()); // 1:2:3

        MutableTriple<Integer, Integer, Integer> triple1 = MutableTriple.of(4, 5, 6);
        System.out.println(triple1); // (4,5,6)
        triple1.setLeft(7);
        triple1.setMiddle(8);
        triple1.setRight(9);
        System.out.println(triple1); // (7,8,9)
    }
}
