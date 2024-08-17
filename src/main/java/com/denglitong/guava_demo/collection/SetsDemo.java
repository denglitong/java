package com.denglitong.guava_demo.collection;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/2
 */
public class SetsDemo {

    public static void main(String[] args) {
        Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");

        Sets.SetView<String> intersection = Sets.intersection(wordsWithPrimeLength, primes);
        System.out.println(intersection);// [two, three, seven]

        System.out.println(intersection.immutableCopy());

        Set<String> evens = ImmutableSet.of("two", "four", "six", "eight");
        Set<List<String>> cartesian = Sets.cartesianProduct(primes, evens);
        // [two, two]
        // [two, four]
        // [two, six]
        // [two, eight]
        // [three, two]
        // [three, four]
        // [three, six]
        // [three, eight]
        // [five, two]
        // [five, four]
        // [five, six]
        // [five, eight]
        // [seven, two]
        // [seven, four]
        // [seven, six]
        // [seven, eight]
        cartesian.forEach(System.out::println);
        System.out.println("================");

        // []
        // [two]
        // [four]
        // [two, four]
        // [six]
        // [two, six]
        // [four, six]
        // [two, four, six]
        // [eight]
        // [two, eight]
        // [four, eight]
        // [two, four, eight]
        // [six, eight]
        // [two, six, eight]
        // [four, six, eight]
        // [two, four, six, eight]
        Sets.powerSet(evens).forEach(System.out::println);
    }
}
