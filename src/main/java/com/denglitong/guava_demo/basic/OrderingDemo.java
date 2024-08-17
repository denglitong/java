package com.denglitong.guava_demo.basic;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/31
 */
public class OrderingDemo {

    public static void main(String[] args) {
        Ordering<String> byLengthOrdering = new Ordering<String>() {
            @Override
            public int compare(@Nullable String left, @Nullable String right) {
                return Ints.compare(left.length(), right.length());
            }
        };

        String[] strings = new String[]{"hello", "world", "nice", "to", "meet", "you"};
        Arrays.sort(strings, byLengthOrdering);
        // [to, you, nice, meet, hello, world]
        System.out.println(Arrays.toString(strings));

        Arrays.sort(strings, Ordering.usingToString());
        // [hello, meet, nice, to, world, you]
        System.out.println(Arrays.toString(strings));

        // 链式比较器，后面的函数包了前面的函数，从后往前读
        Ordering<Foo> ordering = Ordering.natural()
                .nullsFirst()
                .onResultOf(new Function<Foo, String>() {
                    public @Nullable String apply(@Nullable Foo foo) {
                        return foo.sortedBy;
                    }
                });

        Foo[] foos = new Foo[]{
                new Foo("hello", 1),
                new Foo("world", 2),
                new Foo("thank", 3),
                new Foo("you", 4),
                new Foo("", 5),
                new Foo(null, 6),
                new Foo("indian", 7),
                new Foo("mi fans", 8),
        };
        Arrays.sort(foos, ordering);

        // [OrderingDemo.Foo[notSortedBy=6,sortedBy=<null>],
        // OrderingDemo.Foo[notSortedBy=5,sortedBy=],
        // OrderingDemo.Foo[notSortedBy=1,sortedBy=hello],
        // OrderingDemo.Foo[notSortedBy=7,sortedBy=indian],
        // OrderingDemo.Foo[notSortedBy=8,sortedBy=mi fans],
        // OrderingDemo.Foo[notSortedBy=3,sortedBy=thank],
        // OrderingDemo.Foo[notSortedBy=2,sortedBy=world],
        // OrderingDemo.Foo[notSortedBy=4,sortedBy=you]]
        System.out.println(Arrays.toString(foos));

        Arrays.sort(foos, ordering.reverse());
        System.out.println(Arrays.toString(foos));

        Integer[] ints = new Integer[]{1, 3, 5, 2, 6, 4, 9, 7, 8, 0};
        Ordering<Integer> intOrder = Ordering.natural();
        Iterator<Integer> iterator = Arrays.stream(ints).iterator();

        System.out.println(intOrder.greatestOf(iterator, 3)); // [9,8,7]
        System.out.println(intOrder.isOrdered(Arrays.asList(ints))); // false

        Iterator<Integer> iterator1 = Arrays.stream(ints).iterator();
        // iterable: () -> iterator
        System.out.println(intOrder.isOrdered(() -> iterator1)); // false

        ints = new Integer[]{2, 5, 4, 6, 3, 2};
        Iterator<Integer> iterator2 = Arrays.stream(ints).iterator();
        System.out.println(intOrder.sortedCopy(() -> iterator2)); // [2, 2, 3, 4, 5, 6]

        System.out.println(intOrder.min(2, 4, 3)); // 2

        Iterator<Integer> iterator3 = Arrays.stream(ints).iterator();
        System.out.println(intOrder.min(() -> iterator3)); // 2
    }

    private static class Foo implements Comparable<Foo> {
        @Nullable String sortedBy;
        int notSortedBy;

        public Foo(@Nullable String sortedBy, int notSortedBy) {
            this.sortedBy = sortedBy;
            this.notSortedBy = notSortedBy;
        }

        public int compareTo(Foo right) {
            return StringUtils.compare(sortedBy, right.sortedBy);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this,
                    ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }
}
