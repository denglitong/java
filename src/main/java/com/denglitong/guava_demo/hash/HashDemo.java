package com.denglitong.guava_demo.hash;

import com.google.common.base.Charsets;
import com.google.common.hash.*;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/10
 */
public class HashDemo {

    public static void main(String[] args) {
        // using Hashing.sha256() for security
        HashFunction hf = Hashing.md5();
        HashCode hc = hf.newHasher()
                .putLong(100)
                .putString("hello, world", Charsets.UTF_8)
                .hash();
        System.out.println(hc.toString()); // 0a941a285b962295e5d4e38ea0699e32

        HashFunction hf1 = Hashing.sha256();
        HashCode hc1 = hf1.newHasher()
                .putLong(100)
                .putString("hello, world", Charsets.UTF_8)
                .putObject(
                        new Person(1, "Leon Deng", 1993),
                        Person.funnel())
                .hash();
        System.out.println(hc1); // 93eea26de4612ec62daa9ed1d788114df270c7a79ab00c02cab4ab33b15964c8

        // 布隆过滤器 是哈希运算的一项优雅运用，它允许你检测某个对象一定不再过滤器中（没有哈希值）
        // 有了哈希值也不一定存在，因为哈希值可能冲突，但是没有哈希值，一定不存在
        BloomFilter<Person> friends = BloomFilter.create(Person.funnel(), 500, 0.01);
        for (int i = 0; i < 500; i++) {
            friends.put(new Person(i, "Person " + i, 1993 + i % 25));
        }
        // long time past...
        int random = RandomUtils.nextInt(0, 499);
        Person dude = new Person(random,
                "Person " + random,
                1993 + random % 25);
        if (friends.mightContain(dude)) {
            System.out.println("Person dude might contain.");
        } else {
            System.out.println("Person dude must not contain.");
        }
        System.out.println(dude);

    }

    private static class Person {
        private int id;
        private String name;
        private int birthYear;

        public Person(int id, String name, int birthYear) {
            this.id = id;
            this.name = name;
            this.birthYear = birthYear;
        }

        public static Funnel<Person> funnel() {
            return new Funnel<Person>() {
                @Override
                public void funnel(Person from, PrimitiveSink into) {
                    into.putInt(from.id)
                            .putString(from.name, Charsets.UTF_8)
                            .putInt(from.birthYear);
                }
            };
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", birthYear=" + birthYear +
                    '}';
        }
    }
}
