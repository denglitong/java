package com.denglitong.guava_demo.collection;

import com.google.common.collect.*;

import java.util.Map;
import java.util.Set;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/1
 */
public class ImmutableCollectionDemo {

    public static void main(String[] args) {
        ImmutableSet<String> COLOR_NAMES = ImmutableSet.of(
                "red",
                "orange",
                "yellow",
                "green",
                "blue",
                "purple"
        );
        System.out.println(COLOR_NAMES);
        System.out.println(COLOR_NAMES.contains("yellow"));

        ImmutableSet<Color> COLORS = ImmutableSet.<Color>builder()
                .add(new Color("red", 1))
                .add(new Color("orange", 2), new Color("yellow", 3))
                .build();
        System.out.println(COLORS);

        COLORS.asList().forEach(System.out::println);

        ImmutableSet<String> ALPHA = ImmutableSortedSet.of("a", "d", "b", "a", "c");
        System.out.println(ALPHA); // [a, b, c, d]
        System.out.println(ALPHA.asList().get(2)); // c
        // System.out.println(ALPHA.asList().get(4)); // ArrayIndexOutIfBoundsException

        ImmutableMap<String, Integer> immutableMap = ImmutableMap.of("k1", 10);
        // immutableMap.replace("k1", 20); // UnsupportedOperationException
        System.out.println(immutableMap);

        Multiset<Integer> multiset = HashMultiset.create();
        multiset.add(10);
        multiset.add(10);
        multiset.add(20);
        multiset.add(20);
        multiset.add(20);
        System.out.println(multiset); // [20 x 3, 10 x 2]
        ImmutableMultiset.builder();

        Multimap<String, Integer> multimap = HashMultimap.create();
        multimap.put("k1", 10);
        multimap.put("k1", 20);
        multimap.put("k2", 30);
        multimap.put("k2", 50);
        multimap.put("k2", 40);
        System.out.println(multimap); // {k1=[20, 10], k2=[40, 30, 50]}
        ImmutableMultimap.builder();

        ListMultimap<String, Integer> multimap1 = ArrayListMultimap.create();
        multimap1.put("k3", 10);
        multimap1.put("k4", 30);
        multimap1.put("k4", 20);
        System.out.println(multimap1); // {k3=[10], k4=[30, 20]}
        ImmutableListMultimap.builder();

        SetMultimap<String, Integer> multimap2 = HashMultimap.create();
        ImmutableSetMultimap.builder();

        // BiMap 要求值的唯一性
        ImmutableBiMap.builder();
        BiMap<String, Integer> biMap = HashBiMap.create();
        biMap.put("k1", 10);
        biMap.put("k1", 10);
        biMap.put("k2", 20);
        // biMap.put("k3", 20); // IllegalArgumentException: value already present: 20
        System.out.println(biMap); // {k1=10, k2=20}
        System.out.println(biMap.get("k2"));

        // 视图关联下的反转 BiMap，修改元素会影响原有 BiMap
        ImmutableBiMap.builder();
        BiMap<Integer, String> biMap1 = biMap.inverse();
        System.out.println(biMap1.get(20)); // k2

        ImmutableClassToInstanceMap.builder();
        ClassToInstanceMap<Number> instanceMap = MutableClassToInstanceMap.create();
        instanceMap.putInstance(Integer.class, Integer.valueOf(10));
        instanceMap.putInstance(Number.class, Integer.valueOf(200));
        System.out.println(instanceMap.getInstance(Integer.class));
        System.out.println(instanceMap.getInstance(Number.class));

        ImmutableTable.builder();
        Table<String, String, Object> table = HashBasedTable.create();
        table.put("a", "javase", 80);
        table.put("b", "javaee", 90);
        table.put("c", "javame", 100);
        table.put("d", "guava", 70);
        for (Table.Cell<String, String, Object> cell : table.cellSet()) {
            System.out.println(cell.getRowKey() + " " + cell.getColumnKey() + " " + cell.getValue());
        }
        System.out.println(table.rowKeySet()); // [a, b, c, d]
        System.out.println(table.columnKeySet()); // [javase, javaee, javame, guava]
        System.out.println(table.values()); // [80, 90, 100, 70]

        table.put("a", "apache-commons", 75);
        for (Map.Entry<String, Object> entry : table.row("a").entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        // {a={javase=80, apache-commons=75}, b={javaee=90}, c={javame=100}, d={guava=70}}
        System.out.println(table);
        // {javase={a=80}, apache-commons={a=75}, javaee={b=90}, javame={c=100}, guava={d=70}}
        System.out.println(Tables.transpose(table));

    }

    private static class Foo {
        Set<Color> colors;

        public Foo(Set<Color> colors) {
            this.colors = ImmutableSet.copyOf(colors); // defensive copy
        }
    }

    private static class Color {
        private String name;
        private Integer weight;

        public Color(String value, Integer weight) {
            this.name = value;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "name='" + name + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }
}
