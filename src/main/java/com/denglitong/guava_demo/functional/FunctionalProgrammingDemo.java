package com.denglitong.guava_demo.functional;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/11
 */
public class FunctionalProgrammingDemo {

    public static void main(String[] args) {
        // ①
        Function<String, Integer> lengthFunc = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };

        System.out.println(lengthFunc.apply("hello world"));

        // ②
        Predicate<String> allCaps = new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                return CharMatcher.javaUpperCase().matchesAllOf(input);
            }
        };

        System.out.println(allCaps.test("HELLO")); // true

        // ③
        Iterable<String> strings = Iterables.concat(Arrays.asList(
                "me", "I", "YOU", "HELLO", "good afternoon", "SYSTEM",
                "WORLD", "multiset"
        ));
        Multiset<Integer> lengths = HashMultiset.create(
                Iterables.transform(Iterables.filter(strings, allCaps), lengthFunc));

        System.out.println(lengths);

        lengths = HashMultiset.create(
                FluentIterable.from(strings)
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean apply(@Nullable String input) {
                                return CharMatcher.javaUpperCase().matchesAllOf(input);
                            }
                        })
                        .transform(new Function<String, Integer>() {
                            @Override
                            public @Nullable Integer apply(@Nullable String input) {
                                return input.length();
                            }
                        }));
        System.out.println(lengths);
    }
}
