package com.denglitong.apache_commons_demo.builder;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/24
 */
public class CompareToBuilderDemo {

    public static void main(String[] args) {
        Boy boy = new Boy(1, "leon", 18, "basketball,footbal");
        Boy boy1 = new Boy(1, "leon", 18, "singing,running");
        System.out.println(CompareToBuilder.reflectionCompare(boy, boy1)); // 0
        System.out.println(CompareToBuilder.reflectionCompare(boy, boy1, true)); // -17

        CompareToBuilder builder = new CompareToBuilder();
        builder.append(boy, boy1);
        System.out.println(builder.build()); // 0
        System.out.println(builder.toComparison()); // -1

        boy1.number = boy.number + 10;
        builder.append(boy, boy1);
        System.out.println(builder.build()); // -1
        System.out.println(builder.toComparison()); // -1
    }

    public static class Boy implements Comparable<Boy> {
        private int number;
        private String name;
        private int age;
        transient String hobbies;

        public Boy(int number, String name, int age, String hobbies) {
            this.number = number;
            this.name = name;
            this.age = age;
            this.hobbies = hobbies;
        }

        @Override
        public int compareTo(Boy boy) {
            return Integer.compare(number, boy.number) +
                    StringUtils.compare(name, boy.name) +
                    Integer.compare(age, boy.age);
        }
    }

}
