package com.denglitong.guava_demo.collection;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/1
 */
public class MultimapsDemo {

    public static void main(String[] args) {
        // ① ListMultimap
        ListMultimap<String, Integer> listMultimap = ArrayListMultimap.create();
        // listMultimap = LinkedListMultimap.create();
        listMultimap.put("cat", 5);
        listMultimap.put("dog", 10);
        listMultimap.put("cat", 20);
        System.out.println(listMultimap); // {cat=[5, 20], dog=[10]}
        listMultimap.get("cat").forEach(System.out::println); // 5 20

        listMultimap.put("sky", 50);
        listMultimap.put("sky", 20);
        listMultimap.put("sky", 100);
        System.out.println(listMultimap); // {sky=[50, 20, 100], cat=[5, 20], dog=[10]}
        listMultimap.remove("sky", 20);
        System.out.println(listMultimap); // {sky=[50, 100], cat=[5, 20], dog=[10]}
        listMultimap.removeAll("sky");
        System.out.println(listMultimap); // {cat=[5, 20], dog=[10]}

        System.out.println(listMultimap.get("<null>")); // []
        System.out.println(listMultimap.get("<null>").isEmpty()); // true
        System.out.println(listMultimap.get("<null>").size()); // 0

        // Modify
        List<Integer> catValues = listMultimap.get("cat");
        for (int i = 0; i < catValues.size(); i++) {
            catValues.set(i, catValues.get(i) + 5);
        }
        System.out.println(listMultimap); // {cat=[10, 25], dog=[10]}

        // ② SetMultimap
        SetMultimap<String, Integer> setMultimap = HashMultimap.create();
        // setMultimap = LinkedHashMultimap.create();
        // setMultimap = TreeMultimap.create();
        setMultimap.put("cat", 5);
        setMultimap.put("dog", 10);
        setMultimap.put("cat", 20);
        System.out.println(setMultimap); // {cat=[20, 5], dog=[10]}
        setMultimap.get("cat").forEach(System.out::println); // 20 5

        setMultimap.put("sky", 50);
        setMultimap.put("sky", 20);
        setMultimap.put("sky", 100);
        System.out.println(setMultimap); // {sky=[20, 100, 50], cat=[20, 5], dog=[10]}
        setMultimap.remove("sky", 20);
        System.out.println(setMultimap); // {sky=[100, 50], cat=[20, 5], dog=[10]}
        setMultimap.removeAll("sky");
        System.out.println(setMultimap); // {cat=[20, 5], dog=[10]}

        System.out.println(setMultimap.get("<null>")); // []
        System.out.println(setMultimap.get("<null>").isEmpty()); // true

        // Modify
        setMultimap.put("dog", 20);
        System.out.println(setMultimap); // {cat=[20, 5], dog=[20, 10]}
        setMultimap.putAll("cat", () -> Arrays.asList(10, 15).iterator());
        System.out.println(setMultimap); // {cat=[10, 20, 5, 15], dog=[20, 10]}
        setMultimap.replaceValues("cat", () -> Arrays.asList(15, 10).iterator());
        System.out.println(setMultimap); // {cat=[10, 15], dog=[20, 10]}

        System.out.println(setMultimap.asMap().get("<null>")); // null
        // cat - 10
        // cat - 15
        // dog - 20
        // dog - 10
        setMultimap.entries().forEach(entry -> {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        });
        System.out.println(setMultimap.keys()); // [cat x 2, dog x 2]
        System.out.println(setMultimap.keySet()); // [cat, dog]
        System.out.println(setMultimap.values()); // [10, 15, 20, 10]
        System.out.println(setMultimap.asMap().values()); // [[10, 15], [20, 10]]

        // invertFrom
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.putAll("b", Ints.asList(2, 4, 6));
        multimap.putAll("a", Ints.asList(4, 2, 1));
        multimap.putAll("c", Ints.asList(2, 5, 3));
        System.out.println(multimap); // {a=[4, 2, 1], b=[2, 4, 6], c=[2, 5, 3]}
        System.out.println(multimap.get("b"));

        TreeMultimap<Integer, String> inverse = Multimaps.invertFrom(multimap, TreeMultimap.create());
        System.out.println(inverse); // {1=[a], 2=[a, b, c], 3=[c], 4=[a, b], 5=[c], 6=[b]}
        System.out.println(inverse.get(2));

        // forMap
        Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 1, "c", 2);
        SetMultimap<String, Integer> setMultimap1 = Multimaps.forMap(map);
        System.out.println(setMultimap1);
        Multimap<Integer, String> inverse1 = Multimaps.invertFrom(setMultimap1, HashMultimap.create());
        System.out.println(inverse1); // {1=[a, b], 2=[c]}
        System.out.println(inverse1.get(1));
    }
}
