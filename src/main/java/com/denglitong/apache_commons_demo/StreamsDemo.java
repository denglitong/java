package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.stream.Streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/21
 */
public class StreamsDemo {

    public static void main(String[] args) {
        Collection<Integer> collection = Arrays.asList(1, 3, 5, 7, 9);
        Streams.FailableStream<Integer> stream = Streams.stream(collection);
        stream.forEach(System.out::println); // marked terminated after finish
        // stream.forEach(System.out::println); // IllegalStateExceptionThis stream is already terminated

        Stream<String> stream1 = Stream.of("hello", "from", "the", "other", "side");
        Streams.FailableStream<String> stream2 = Streams.stream(stream1);
        System.out.println(stream2.stream().skip(2).findFirst());

        Collector<String, ?, String[]> collector = Streams.toArray(String.class);
        Streams.ArrayCollector<String> arrayCollector = (Streams.ArrayCollector<String>)collector;
        List<String> list = new ArrayList<>();
        arrayCollector.accumulator().accept(list, "hello");
        arrayCollector.accumulator().accept(list, "world");
        System.out.println(list); // [hello, world]
    }
}
