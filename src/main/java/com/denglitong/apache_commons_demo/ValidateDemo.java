package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.Validate;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/24
 */
public class ValidateDemo {

    public static void main(String[] args) {
        int id = -1;
        // IllegalArgumentException: The validated expression is false
        // Validate.isTrue(id > 0);

        // IllegalArgumentException: The id must be greater than 0: -1
        // Validate.isTrue(id > 0, "The id must be greater than 0: %d", id);

        Integer age = null;
        // NullPointerException：The validated object is null
        // Validate.notNull(arg);

        // NullPointerException: The arg must be not null
        // Validate.notNull(age, "The age must be not null");

        String[] names = new String[]{};
        // Validate.notEmpty(names, "The names must be not empty");

        // Validate.notBlank("   ", "The string must be not blank");

        names = new String[]{"hello", null};
        // Validate.noNullElements(names, "The names must not contain null value");

        // IndexOutOfBoundsException: The index out of bound: 2 > 1
        // Validate.validIndex(names, 2, "The index out of bound: %d > %d",
        //         2, ArrayUtils.getLength(names) - 1);

        // IllegalStateException: The id must be greater than 0: -1
        // Validate.validState(id > 0, "The id must be greater than 0: %d", id);

        String password = "hello";
        // Validate.matchesPattern(password, "[a-zA-Z0-9-_\\.]{8,}",
        //         "The password length must greater than 8: %d", password.length());

        // IllegalArgumentException: The value must be a number: NaN
        // Validate.notNaN(Double.NaN, "The value must be a number: %f", Double.NaN);

        // IllegalArgumentException: The value must be finite: Infinity
        // Validate.finite(Double.POSITIVE_INFINITY, "The value must be finite: %f",
        //         Double.POSITIVE_INFINITY);

        // IllegalArgumentException: The age must be [0, 200]: 300
        // Validate.inclusiveBetween(0, 200, 300, "The age must be [0, 200]: %d", 300);

        // IllegalArgumentException: The radius must be (3.14, 12.56): 3.140000
        // Validate.exclusiveBetween(3.14, 12.56, 3.14, "The radius must be (3.14, 12.56): %f", 3.14);

        // Validate.isInstanceOf(String.class, 1024, "The arg must be a String instance: %s", 1024);

        // Cannot assign a java.lang.Number to a java.lang.Integer
        Validate.isAssignableFrom(Integer.class, Number.class);
    }
}
