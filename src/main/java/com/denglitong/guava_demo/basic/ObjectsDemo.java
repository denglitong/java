package com.denglitong.guava_demo.basic;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/30
 */
public class ObjectsDemo {

    public static void main(String[] args) {
        System.out.println(Objects.equal("a", "a")); // true
        System.out.println(Objects.equal(null, "a")); // false
        System.out.println(Objects.equal(new Simple(1, 2), new Simple(1, 2))); // true

        // Simple{a=100, b=200}
        System.out.println(
                MoreObjects.toStringHelper(Simple.class)
                        .add("a", 100)
                        .add("b", 200)
                        .toString());
        
        System.out.println(new Simple(1, 2).compareTo(new Simple(1, 2))); // 0
        System.out.println(new Simple(1, 4).compareTo(new Simple(1, 2))); // 1
        System.out.println(new Simple(1, 4).compareTo(new Simple(1, 10))); // -1

    }

    private static class Simple implements Comparable<Simple> {
        private Integer a;
        private Integer b;

        public Simple(Integer a, Integer b) {
            this.a = a;
            this.b = b;
        }

        // equals and hashcode template from Guava

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Simple simple = (Simple)o;
            return Objects.equal(a, simple.a) && Objects.equal(b, simple.b);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(a, b);
        }

        public int compareTo(Simple rhs) {
            // 使用 Comparison 链式比较，直至发现非 0 的结果然后返回
            return ComparisonChain.start()
                    .compare(this.a, rhs.a)
                    .compare(this.b, rhs.b)
                    .result();
        }
    }
}
