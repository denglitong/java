package com.denglitong.guava_demo.collection;

import com.google.common.base.Function;
import com.google.common.collect.*;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/2
 */
public class MapsDemo {

    public static void main(String[] args) {
        // ① Maps.uniqueIndex(iterable, keyFunc)
        Iterable<String> strings = Iterables.concat(
                Lists.newArrayList("hello", "from", "the", "I", "thousand"));

        Function<String, Integer> keyFunc = new Function<String, Integer>() {
            @Override
            public @Nullable Integer apply(@Nullable String string) {
                return StringUtils.length(string);
            }
        };

        ImmutableMap<Integer, String> stringsByIndex = Maps.uniqueIndex(strings, keyFunc);
        System.out.println(stringsByIndex); // {5=hello, 4=from, 3=the, 1=I, 8=thousand}
        System.out.println(stringsByIndex.get(8)); // thousand

        // ② Multimaps.index(iterable, keyFunc)
        strings = Iterables.concat(
                Lists.newArrayList("hello", "from", "the", "I", "thousand"),
                Lists.newArrayList("to", "her"));
        // IllegalArgumentException: Multiple entries with same key: 3=her and 3=the
        // stringsByIndex = Maps.uniqueIndex(strings, keyFunc);

        ImmutableMultimap<Integer, String> stringsByIndex1 = Multimaps.index(strings, keyFunc);
        // {5=[hello], 4=[from], 3=[the, her], 1=[I], 8=[thousand], 2=[to]}
        System.out.println(stringsByIndex1);
        System.out.println(stringsByIndex1.get(3)); // [the, her]

        // ③ Maps.difference(map, map)
        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> right = ImmutableMap.of("b", 2, "c", 5, "d", 6);
        MapDifference<String, Integer> diff = Maps.difference(left, right);

        // not equal: only on left={a=1}: only on right={d=6}: value differences={c=(3, 5)}
        System.out.println(diff);
        System.out.println(diff.entriesInCommon()); // {b=2}
        System.out.println(diff.entriesDiffering()); // {c=(3, 5)}
        System.out.println(diff.entriesOnlyOnLeft()); // {a=1}
        System.out.println(diff.entriesOnlyOnRight()); // {d=6}
    }
}
