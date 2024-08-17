package com.denglitong.guava_demo.collection;

import com.google.common.collect.AbstractSequentialIterator;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Iterator;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/2
 */
public class AbstractSequentialIteratorDemo {

    public static void main(String[] args) {
        Iterator<Integer> powerOfTwoLess100 = new AbstractSequentialIterator<Integer>(1) {
            @Override
            protected @Nullable Integer computeNext(Integer previous) {
                return (previous > 100) ? null : previous * 2;
            }
        };
        // 1
        // 2
        // 4
        // 8
        // 16
        // 32
        // 64
        // 128
        powerOfTwoLess100.forEachRemaining(System.out::println);
    }
}
