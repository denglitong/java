package com.denglitong.guava_demo.collection;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;

import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/2
 */
public class PeekingIteratorDemo {

    public static void main(String[] args) {
        List<Integer> result = Lists.newArrayList();
        List<Integer> source = Lists.newArrayList(1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4);

        PeekingIterator<Integer> iter = Iterators.peekingIterator(source.iterator());
        while (iter.hasNext()) {
            Integer current = iter.next();
            while (iter.hasNext() && iter.peek().equals(current)) {
                iter.next();
            }
            result.add(current);
        }

        System.out.println(result); // [1, 2, 3, 4]
    }
}
