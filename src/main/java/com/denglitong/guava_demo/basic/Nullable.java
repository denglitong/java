package com.denglitong.guava_demo.basic;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/30
 */
public class Nullable {

    public static void main(String[] args) {
        Optional<Integer> possible = Optional.of(5);
        System.out.println(possible); // Optional.of(5)
        System.out.println(possible.asSet()); // [5]

        // possible = Optional.of(null); // NullPointerException

        possible = Optional.fromNullable(null);
        System.out.println(possible); // Optional.absent()

        possible = Optional.absent();
        // IllegalStateException: Optional.get() cannot be called on an absent value
        // System.out.println(possible.get());

        System.out.println(possible.or(-1)); // -1
        System.out.println(possible.orNull()); // null

        System.out.println(possible.asSet()); // []

        System.out.println(Strings.emptyToNull("")); // null
        System.out.println(Strings.nullToEmpty(null)); // ""
        System.out.println(Strings.isNullOrEmpty("")); // true
        System.out.println(Strings.isNullOrEmpty(null)); // true
    }
}
