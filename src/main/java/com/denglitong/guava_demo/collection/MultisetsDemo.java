package com.denglitong.guava_demo.collection;

import com.google.common.collect.*;

import java.util.Arrays;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/1
 */
public class MultisetsDemo {

    public static void main(String[] args) {
        String line = "twinkle twinkle little star";
        Multiset<String> words = HashMultiset.create();
        words.addAll(Arrays.asList(line.split(" ")));
        System.out.println(words); // [twinkle x 2, star, little]

        // twinkle
        // twinkle
        // star
        // little
        words.iterator().forEachRemaining(System.out::println);
        System.out.println(words.size()); // 4

        System.out.println(words.count("twinkle")); // 2
        System.out.println(words.count("little")); // 1
        System.out.println(words.count("<null>")); // 0
        // twinkle 2
        // star 1
        // little 1
        words.entrySet().forEach(entry -> {
            System.out.println(entry.getElement() + " " + entry.getCount());
        });
        // twinkle
        // star
        // little
        words.elementSet().forEach(System.out::println);

        words.remove("twinkle", 2);
        System.out.println(words); // [star, little]
        words.remove("star"); // words.setCount("star", 0);
        System.out.println(words); // [little]
        words.setCount("twinkle", 3);
        System.out.println(words); // [twinkle x 3, little]

        HashMultiset.create();
        TreeMultiset.create();
        LinkedHashMultiset.create();
        ConcurrentHashMultiset.create();
        ImmutableMultiset.builder();

        // containsAll, containsOccurrence, removeOccurrences, removeAll
        Multiset<String> multiset1 = HashMultiset.create();
        multiset1.add("a", 2);
        Multiset<String> multiset2 = HashMultiset.create();
        multiset2.add("a", 5);
        System.out.println(multiset1.containsAll(multiset2)); // true
        System.out.println(Multisets.containsOccurrences(multiset1, multiset2)); // false
        System.out.println(Multisets.containsOccurrences(multiset2, multiset1)); // true

        Multisets.removeOccurrences(multiset2, multiset1);
        System.out.println(multiset2); // [a x 3]
        multiset2.removeAll(multiset1);
        System.out.println(multiset2.isEmpty()); // true

        // copyHighestCountFirst
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a", 3);
        multiset.add("b", 5);
        multiset.add("c", 1);
        ImmutableMultiset<String> highestCountFirst = Multisets.copyHighestCountFirst(multiset);
        System.out.println(highestCountFirst); // [b x 5, a x 3, c]
    }
}
