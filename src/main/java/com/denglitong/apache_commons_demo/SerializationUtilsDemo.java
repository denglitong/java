package com.denglitong.apache_commons_demo;

import com.sun.xml.messaging.saaj.util.ByteInputStream;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/21
 */
public class SerializationUtilsDemo {

    public static void main(String[] args) {
        Employee employee = new Employee(1, "Leon", 18, 1800.00);

        byte[] bytes = SerializationUtils.serialize(employee);
        System.out.println(Arrays.toString(bytes));

        Employee employee1 = SerializationUtils.deserialize(bytes);
        System.out.println(employee1);

        SerializationUtils.roundtrip(employee);

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        SerializationUtils.serialize(employee, byteOutputStream);

        ByteInputStream byteInputStream = new ByteInputStream(byteOutputStream.toByteArray(), byteOutputStream.size());
        Employee employee2 = SerializationUtils.deserialize(byteInputStream);
        System.out.println(employee2);
    }

    static class Employee implements Serializable {

        private static final long serialVersionUID = 4555049567554743856L;
        private Integer id;
        private String name;
        private int age;
        private double salary;

        public Employee(Integer id, String name, int age, double salary) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
}
