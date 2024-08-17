package com.denglitong.guava_demo.primitive;

import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedInts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/8
 */
public class PrimsArrayDemo {

    public static void main(String[] args) {
        // ① Arrays.asList
        List<Integer> ints = Arrays.asList(-129, 0, 128, 200);
        System.out.println(ints);

        // ② Collection.toArray
        Collection<Integer> collection = new ArrayList<>(10);
        collection.add(1);
        collection.add(2);
        collection.add(3);

        // Object cannot be cast to Integer
        // Integer[] integers = (Integer[])collection.toArray();
        // System.out.println(integers);

        Integer[] integers = collection.toArray(new Integer[0]);
        System.out.println(Arrays.toString(integers)); // [1, 2, 3]

        Number[] numbers = new Number[]{4, 5, 6};
        Number[] numbers1 = collection.toArray(numbers);
        System.out.println(Arrays.toString(numbers1)); // [1, 2, 3]

        // ③ Iterables.concat
        Iterable<Integer> integers1 = Arrays.asList(7, 8, 9);
        Iterable<Integer> integers2 = Arrays.asList(10, 20, 30);
        Iterable<Integer> integers3 = Iterables.concat(integers1, integers2);
        System.out.println(integers3);

        // ④ Ordering.natural.lexicographical
        List<Iterable<String>> strings = Arrays.asList(
                Arrays.asList("hello", "world"),
                Arrays.asList("Zero", "100"),
                Arrays.asList("-0", ".34"));
        strings.sort(Ordering.natural().lexicographical());
        System.out.println(strings); // [[-0, .34], [Zero, 100], [hello, world]]

        // ⑤ compare
        System.out.println(Ints.compare(1, 22));

        // ⑥ cast
        // int maxLong = Ints.checkedCast(Long.MAX_VALUE); // Exception: Out of range
        int maxInt = Ints.saturatedCast(Long.MAX_VALUE);
        System.out.println(maxInt); // 2147483647

        // ⑦ fromByteArray
        System.out.println(Ints.BYTES + " " + Longs.BYTES); // 4 8
        int int1 = Ints.fromByteArray(new byte[]{1, 3, 5, 7, 9});
        System.out.println(int1); // 16975111
        System.out.println(Arrays.toString(Ints.toByteArray(int1))); // [1, 3, 5, 7]

        // ⑧ parseUnsigned, toString(prims, radix)
        int int2 = UnsignedInts.parseUnsignedInt("101010101010", 2);
        System.out.println(int2); // 2730
        System.out.println(UnsignedInts.toString(int2, 2)); // 101010101010
        System.out.println(UnsignedInts.toString(int2, 10)); // 2730

        // ⑨ valueOf, plus, bigInteger, toString
        UnsignedInteger integer = UnsignedInteger.valueOf(100);
        integer = integer.plus(UnsignedInteger.valueOf(120));
        System.out.println(integer);

        BigInteger bigInteger = integer.bigIntegerValue();
        System.out.println(bigInteger);

        System.out.println(integer.toString(2)); // 11011100

    }
}
