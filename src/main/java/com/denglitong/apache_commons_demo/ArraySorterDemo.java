package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.ArraySorter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/14
 * @see Arrays
 */
public class ArraySorterDemo {

    public static void main(String[] args) {
        byte[] bytes = new byte[]{0, 3, -1, 2, 4, 1, -3};
        System.out.println(Arrays.toString(bytes));
        ArraySorter.sort(bytes); // Arrays.sort(bytes);
        System.out.println(Arrays.toString(bytes));

        short[] shorts = new short[]{0, 3000, -1000, 2000, 4000, 1000, -3000};
        System.out.println(Arrays.toString(shorts));
        ArraySorter.sort(shorts);
        System.out.println(Arrays.toString(shorts));

        Type[] types = new Type[]{
                new Type(0), new Type(3), new Type(-1),
                new Type(2), new Type(4), new Type(1),
                new Type(-3)
        };
        System.out.println(Arrays.toString(types));
        ArraySorter.sort(types);
        System.out.println(Arrays.toString(types));

        ArraySorter.sort(types, new Comparator<Type>() {
            // 降序
            public int compare(Type o1, Type o2) {
                return o2.value - o1.value;
            }
        });
        System.out.println(Arrays.toString(types));

    }

    static class Type implements Comparable<Type> {
        private int value;

        public Type(int value) {
            this.value = value;
        }

        // 升序
        public int compareTo(Type o) {
            return value - o.value;
        }

        @Override
        public String toString() {
            return String.format("Type[%2d]", value);
        }
    }
}
