package com.denglitong.guava_demo.collection;

import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

import static com.google.common.collect.BoundType.CLOSED;
import static com.google.common.collect.BoundType.OPEN;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/1
 */
public class SortedMultisetDemo {

    public static void main(String[] args) {
        SortedMultiset<Double> latencies = TreeMultiset.create();
        latencies.add(100.2, 2);
        latencies.add(50.3);
        latencies.add(50.4);
        latencies.add(50.5);
        latencies.add(50.4);
        latencies.add(80.55, 3);

        SortedMultiset<Double> normalLantencies = latencies.subMultiset(50.0, CLOSED, 100.0, OPEN);
        System.out.println(normalLantencies.size());
        System.out.println(normalLantencies); // [50.3, 50.4 x 2, 50.5, 80.55 x 3]
    }
}
