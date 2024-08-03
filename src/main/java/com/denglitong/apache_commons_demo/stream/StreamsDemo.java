package com.denglitong.apache_commons_demo.stream;

import org.apache.commons.lang3.stream.Streams;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/26
 */
public class StreamsDemo {

    public static void main(String[] args) {
        Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5);
        Streams.FailableStream<Integer> stream = Streams.stream(collection);

        System.out.println(stream.anyMatch(i -> i == 3)); // true

        // IllegalStateException: stream has already been operated upon or closed
        // System.out.println(stream.anyMatch(i -> i == 6));

        // // IllegalStateException: stream has already been operated upon or closed
        // System.out.println(stream.stream().count());

        Number[] array = collection.stream().collect(Streams.toArray(Number.class));
        // [1, 2, 3, 4, 5]
        System.out.println(Arrays.toString(array));
    }
}
