package com.denglitong.apache_commons_demo.compare;

import org.apache.commons.lang3.compare.ComparableUtils;

import java.util.function.Predicate;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/25
 */
public class ComparableUtilsDemo {

    public static void main(String[] args) {
        Predicate<Integer> predicate = ComparableUtils.gt(10);
        System.out.println(predicate.test(20)); // true
        System.out.println(predicate.test(10)); // false

        predicate = ComparableUtils.between(10, 20);
        System.out.println(predicate.test(15)); // true
        System.out.println(predicate.test(5)); // false
        System.out.println(predicate.test(25)); // false

        Simple simple1 = new Simple(10);
        Simple simple2 = new Simple(20);

        // [10, 20]
        Predicate<Simple> predicate1 = ComparableUtils.between(simple1, simple2);
        System.out.println(predicate1.test(new Simple(15))); // true
        System.out.println(predicate1.test(new Simple(25))); // false

        // (-∞, 10) U (20, +∞)
        predicate1 = predicate1.negate();
        System.out.println(predicate1.test(new Simple(15))); // false
        System.out.println(predicate1.test(new Simple(25))); // true

        ComparableUtils.ComparableCheckBuilder<Integer> checkBuilder = ComparableUtils.is(20);
        System.out.println(checkBuilder.between(10, 30)); // true
        System.out.println(checkBuilder.greaterThan(30)); // false

        ComparableUtils.ComparableCheckBuilder<Simple> checkBuilder1 = ComparableUtils.is(new Simple(15));
        System.out.println(checkBuilder1.between(simple1, simple2)); // true
        System.out.println(checkBuilder1.lessThan(simple1)); // false
    }

    private static class Simple implements Comparable<Simple> {
        private int value;

        public Simple(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(Simple rhs) {
            return Integer.compare(value, rhs.value);
        }
    }
}
