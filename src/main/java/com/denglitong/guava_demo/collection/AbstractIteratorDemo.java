package com.denglitong.guava_demo.collection;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/2
 */
public class AbstractIteratorDemo {

    public static void main(String[] args) {
        Iterator<String> in = Lists.newArrayList("", " ", null, "hello", "world")
                .iterator();
        Iterator<String> iteratorSkipNulls = new AbstractIterator<String>() {
            @Override
            protected String computeNext() {
                while (in.hasNext()) {
                    String s = in.next();
                    if (StringUtils.isNotBlank(s)) {
                        return s;
                    }
                }
                return endOfData();
            }
        };
        // hello
        // world
        iteratorSkipNulls.forEachRemaining(System.out::println);
    }
}
