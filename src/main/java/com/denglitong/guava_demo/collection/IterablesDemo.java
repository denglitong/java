package com.denglitong.guava_demo.collection;

import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/2
 */
public class IterablesDemo {

    public static void main(String[] args) {
        Iterable<Integer> concatenated = Iterables.concat(
                Ints.asList(1, 2, 3),
                Ints.asList(4, 5, 6));

        Integer lastAdded = Iterables.getLast(concatenated);
        System.out.println(lastAdded); // 6

        // IllegalArgumentException: expected one element but was: <1,2,3,4,5,...>
        // Integer theElement = Iterables.getOnlyElement(concatenated);
    }
}
