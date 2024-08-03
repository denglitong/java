package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.ArraySorter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Map;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/14
 */
public class ArrayUtilsDemo {

    public static void main(String[] args) {
        System.out.println(ArrayUtils.toString(ArrayUtils.EMPTY_BOOLEAN_ARRAY));
        System.out.println(ArrayUtils.toString(ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY));
        System.out.println(ArrayUtils.toString(ArrayUtils.EMPTY_CLASS_ARRAY));

        // ① add(T[], T)
        boolean[] booleans = new boolean[]{true, false};
        System.out.println(ArrayUtils.toString(booleans));
        booleans = ArrayUtils.add(booleans, true);
        System.out.println(ArrayUtils.toString(booleans));

        // ② addAll(T[], T...)
        int[] ints = new int[]{1, 2, 3};
        System.out.println(ArrayUtils.toString(ints));
        ints = ArrayUtils.addAll(ints, 4, 5, 6);
        System.out.println(ArrayUtils.toString(ints));

        // ③ addFirst(T[], T)
        short[] shorts = new short[]{1, 2, 3};
        System.out.println(ArrayUtils.toString(shorts));
        shorts = ArrayUtils.addFirst(shorts, (short)4);
        System.out.println(ArrayUtils.toString(shorts));

        // ④ clone(T[])
        float[] floats = new float[]{1.0f, 2.0f, 3.0f};
        float[] floatsClone = ArrayUtils.clone(floats);
        System.out.println(floats == floatsClone); // false
        System.out.println(Arrays.equals(floats, floatsClone)); // true

        // ⑤ contains(T[], T)
        double[] doubles = new double[]{1.0, 2.0, 3.0};
        System.out.println(ArrayUtils.contains(doubles, 1.0));
        System.out.println(ArrayUtils.contains(doubles, 4.0));

        // ⑥ get(T[], int index)
        Byte[] bytes = new Byte[]{(byte)12, (byte)5, (byte)7};
        System.out.println(ArrayUtils.get(bytes, 0)); // 12
        System.out.println(ArrayUtils.get(bytes, 2)); // 7
        System.out.println(ArrayUtils.get(bytes, 3)); // null
        System.out.println((String)ArrayUtils.get(null, 0)); // null

        // ⑦ get(T[], int index, T defaultValue)
        System.out.println(ArrayUtils.get(bytes, 0, -1)); // 12
        System.out.println(ArrayUtils.get(bytes, 3, -1)); // -1
        System.out.println(ArrayUtils.get(null, 0, -1)); // -1

        // ⑧ getLength(T[])
        System.out.println(ArrayUtils.getLength(bytes));
        System.out.println(ArrayUtils.getLength(null));
        // System.out.println(Array.getLength(null)); // NullPointerException

        // ⑨ indexesOf(T[], T)
        booleans = new boolean[]{true, false, true, false};
        BitSet findIndexes = ArrayUtils.indexesOf(booleans, true);
        System.out.println(findIndexes); // {0 2}

        // ⑩ indexOf(T[], T)
        int firstFindIndex = ArrayUtils.indexOf(booleans, true);
        System.out.println(firstFindIndex); // 0

        // ⑪ insert(int index, T[], T)
        ints = new int[]{10, 20, 30};
        ints = ArrayUtils.insert(0, ints, 40);
        System.out.println(ArrayUtils.toString(ints)); // [40, 10, 20, 30]

        // ⑫ isArrayIndexValid(T[], T)
        bytes = new Byte[]{(byte)12, (byte)5, (byte)7};
        System.out.println(ArrayUtils.isArrayIndexValid(bytes, -1));
        System.out.println(ArrayUtils.isArrayIndexValid(bytes, 1));
        System.out.println(ArrayUtils.isArrayIndexValid(bytes, 5));

        System.out.println(ArrayUtils.isEmpty(bytes));
        System.out.println(ArrayUtils.isNotEmpty(ints));

        System.out.println(ArrayUtils.isSameLength(bytes, ints));
        System.out.println(ArrayUtils.isSameLength(bytes, doubles));

        System.out.println(ArrayUtils.isSameType(bytes, ints)); // false
        System.out.println(ArrayUtils.isSameType(ints, new int[]{})); // true

        // ⑬ isSorted(T[])
        System.out.println(ArrayUtils.isSorted(bytes)); // false
        ArraySorter.sort(bytes);
        System.out.println(ArrayUtils.isSorted(bytes)); // true

        System.out.println(ArrayUtils.lastIndexOf(booleans, false));

        System.out.println(ArrayUtils.toString(ArrayUtils.nullToEmpty(booleans))); // [true, false, true, false]
        boolean[] booleansNull = null;
        System.out.println(ArrayUtils.toString(ArrayUtils.nullToEmpty(booleansNull))); // []

        booleans = ArrayUtils.remove(booleans, 3);
        System.out.println(ArrayUtils.toString(booleans)); // [true, false, true]

        booleans = ArrayUtils.removeAll(booleans, 0, 1);
        System.out.println(ArrayUtils.toString(booleans)); // [true]

        ints = new int[]{1, 2, 3, 2, 1};
        ints = ArrayUtils.removeAllOccurrences(ints, 2);
        System.out.println(ArrayUtils.toString(ints)); // [1,3,1]

        ints = ArrayUtils.removeElement(ints, 1); // remove element first occur
        System.out.println(ArrayUtils.toString(ints)); // [3,1]

        ints = new int[]{1, 2, 3, 2, 1};
        ints = ArrayUtils.removeElements(ints, 1, 2);
        System.out.println(ArrayUtils.toString(ints)); // [3,2,1]

        ints = new int[]{1, 2, 3, 4};
        ArrayUtils.reverse(ints);
        System.out.println(ArrayUtils.toString(ints)); // [4,3,2,1]

        // ⑭ right shift array from 0 to index (index % length)
        ints = new int[]{1, 2, 3, 4, 5};
        ArrayUtils.shift(ints, 0);
        System.out.println(ArrayUtils.toString(ints)); // [1, 2, 3, 4, 5]

        ints = new int[]{1, 2, 3, 4, 5};
        ArrayUtils.shift(ints, 1);
        System.out.println(ArrayUtils.toString(ints)); // [5, 1, 2, 3, 4]

        ints = new int[]{1, 2, 3, 4, 5};
        ArrayUtils.shift(ints, 2);
        System.out.println(ArrayUtils.toString(ints)); // [4, 5, 1, 2, 3]

        ints = new int[]{1, 2, 3, 4, 5};
        ArrayUtils.shuffle(ints); // 按洗牌算法随机排列元素
        System.out.println(ArrayUtils.toString(ints));

        ints = new int[]{10, 20, 30, 40, 50};
        System.out.println(ArrayUtils.toString(ArrayUtils.subarray(ints, 1, 1))); // []
        System.out.println(ArrayUtils.toString(ArrayUtils.subarray(ints, 1, 2))); // []
        System.out.println(ArrayUtils.toString(ArrayUtils.subarray(ints, 1, 3))); // []

        ints = new int[]{10, 20, 30, 40, 50};
        ArrayUtils.swap(ints, 0, 4); // do nothing if index out of bound
        System.out.println(ArrayUtils.toString(ints));

        Integer[] integers = ArrayUtils.toArray(3, 2, 1);
        System.out.println(ArrayUtils.toString(integers));

        String[][] maps = new String[][]{
                {"HELLO", "WORLD"},
                {"GOOD", "MORNING"}
        };
        Map<Object, Object> wordsPair = ArrayUtils.toMap(maps);
        System.out.println(wordsPair);

        System.out.println(ArrayUtils.toString(ints));
        integers = ArrayUtils.toObject(ints);
        System.out.println(ArrayUtils.toString(integers));

        ints = ArrayUtils.toPrimitive(integers);
        System.out.println(ArrayUtils.toString(ints));

        integers = null;
        System.out.println(ArrayUtils.toString(integers));
        System.out.println(ArrayUtils.toString(integers, "null array"));

        integers = new Integer[]{1, null, 3};
        // System.out.println(ArrayUtils.toString(ArrayUtils.toStringArray(integers))); // NullPointerException
        System.out.println(ArrayUtils.toString(
                ArrayUtils.toStringArray(integers, "null array")));
    }
}
