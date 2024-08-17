package com.denglitong.guava_demo.collection;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/2
 */
public class ListsDemo {

    public static void main(String[] args) {
        List<Integer> countUp = Ints.asList(1, 2, 3, 4, 5);
        List<Integer> countDown = Lists.reverse(countUp);
        System.out.println(countDown); // [5, 4, 3, 2, 1]

        List<List<Integer>> parts = Lists.partition(countUp, 2);
        System.out.println(parts); // [[1, 2], [3, 4], [5]]

    }
}
