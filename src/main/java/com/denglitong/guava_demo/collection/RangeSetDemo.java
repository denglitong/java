package com.denglitong.guava_demo.collection;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/1
 */
public class RangeSetDemo {

    public static void main(String[] args) {
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10));
        System.out.println(rangeSet); // [[1..10]]
        rangeSet.add(Range.closedOpen(11, 15));
        System.out.println(rangeSet); // [[1..10], [11..15)]
        rangeSet.add(Range.closedOpen(18, 20));
        System.out.println(rangeSet); // [[1..10], [11..15), [18..20)]

        System.out.println(rangeSet.contains(17)); // false
        System.out.println(rangeSet.contains(14)); // true
        System.out.println(rangeSet.rangeContaining(14)); // [11..15)

        System.out.println(rangeSet.intersects(Range.open(10, 11))); // false

        System.out.println(rangeSet.complement()); // [(-∞..1), (10..11), [15..18), [20..+∞)]
        System.out.println(rangeSet.subRangeSet(Range.open(8, 18))); // [(8..10], [11..15)]

        // [1..10]
        // [11..15)
        // [18..20)
        rangeSet.asRanges().forEach(System.out::println);
        System.out.println(rangeSet.encloses(Range.closed(5, 9))); // true

        System.out.println(rangeSet.span()); // [1..20) 包括RangeSet中所有区间的最小扩展区间
    }
}
