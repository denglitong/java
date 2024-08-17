package com.denglitong.guava_demo.basic;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.*;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/30
 */
public class PreconditionsDemo {

    public static void main(String[] args) {
        // method1(3, 2); // IllegalArgumentException: Expected a < b, but 3 > 2
        // method2((Integer)null); // NullPointerException：Argument was null but expected not null
        // method3("abc"); // IllegalStateException: Expected s.length > 0 but found s[abc]

        Integer[] array = new Integer[]{1, 6, 3, 4, 5};
        // IndexOutOfBoundsException index (5) must be less than size (5)
        checkPositionIndex(5, array.length); // ok
        checkPositionIndexes(1, 5, array.length); // ok
        checkPositionIndexes(0, 5, array.length); // ok
        // IndexOutOfBoundsException: end index (6) must not be greater than size (5)
        checkPositionIndexes(0, 6, array.length);
    }

    private static void method1(int a, int b) {
        checkArgument(a >= 0);
        checkArgument(b >= 0);
        checkArgument(a < b, "Expected a < b, but %s > %s", a, b);
    }

    private static void method2(Integer a) {
        checkNotNull(a, "Argument was %s but expected not null", a);
    }

    private static void method3(String s) {
        checkState(StringUtils.length(s) > 6,
                "Expected s.length > 0 but found s[%s]", s);
    }

}
