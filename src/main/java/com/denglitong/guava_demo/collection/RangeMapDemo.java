package com.denglitong.guava_demo.collection;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/1
 */
public class RangeMapDemo {

    public static void main(String[] args) {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();

        rangeMap.put(Range.closed(1, 10), "foo");
        System.out.println(rangeMap); // [[1..10]=foo]

        rangeMap.put(Range.open(3, 6), "bar");
        System.out.println(rangeMap);// [[1..3]=foo, (3..6)=bar, [6..10]=foo]

        rangeMap.put(Range.open(10, 20), "foo");
        // [[1..3]=foo, (3..6)=bar, [6..10]=foo, (10..20)=foo]
        System.out.println(rangeMap);

        System.out.println(rangeMap.asMapOfRanges().containsKey(Range.closed(4, 7))); // false
        System.out.println(rangeMap.subRangeMap(Range.closed(4, 7))); // {[4..6)=bar, [6..7]=foo}

        System.out.println(rangeMap.asDescendingMapOfRanges()); // {(10..20)=foo, [6..10]=foo, (3..6)=bar, [1..3]=foo}

        System.out.println(rangeMap.get(5)); // bar

        System.out.println(rangeMap.getEntry(5)); // (3..6)=bar

        rangeMap.putCoalescing(Range.closed(5, 8), "tom");
        // [[1..3]=foo, (3..5)=bar, [5..8]=tom, (8..10]=foo, (10..20)=foo]
        System.out.println(rangeMap);

        // [1..3] - foo
        // (3..5) - bar
        // [5..8] - tom
        // (8..10] - foo
        // (10..20) - foo
        rangeMap.asMapOfRanges().entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        });
    }
}
