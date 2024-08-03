package com.denglitong.apache_commons_demo.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.*;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/25
 */
public class ToStringBuiderDemo {

    public static void main(String[] args) {
        Student student = new Student(1, "leon", 17);

        // com.denglitong.builder.ToStringBuiderDemo$Student@49476842[age=17,name=leon,number=1]
        System.out.println(ToStringBuilder.reflectionToString(student));
        // ToStringBuiderDemo.Student[age=17,name=leon,number=1]
        System.out.println(ToStringBuilder.reflectionToString(student, SHORT_PREFIX_STYLE));
        // 17,leon,1
        System.out.println(ToStringBuilder.reflectionToString(student, SIMPLE_STYLE));
        // [age=17,name=leon,number=1]
        System.out.println(ToStringBuilder.reflectionToString(student, NO_CLASS_NAME_STYLE));

        ToStringBuilder builder = new ToStringBuilder(student, SHORT_PREFIX_STYLE);
        // Student{number=1, name='leon', age=17}
        System.out.println(builder.getObject());

        // ToStringBuiderDemo.Student[]
        System.out.println(builder.build());

        builder.append("flag1");
        // ToStringBuiderDemo.Student[]flag1]
        System.out.println(builder.build());

        builder.append("key2", "flag2");
        // ToStringBuiderDemo.Student[]flag1]key2=flag2]
        System.out.println(builder.build());

        builder.append("key3", "flag3", true);
        // ToStringBuiderDemo.Student[]flag1]key2=flag2]key3=flag3]
        System.out.println(builder.build());

        Student student1 = new Student(2, "deng", 18);
        builder.appendAsObjectToString(student1);
        System.out.println(builder.build());
    }

    private static class Student {
        private int number;
        private String name;
        private int age;

        public Student(int number, String name, int age) {
            this.number = number;
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "number=" + number +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
