package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.ClassUtils;

import java.util.Arrays;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/15
 */
public class ClassUtilsDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(ClassUtils.getShortClassName(String.class)); // String
        System.out.println(ClassUtils.getSimpleName(String.class)); // String
        System.out.println(ClassUtils.getName(String.class)); // java.lang.String
        System.out.println(ClassUtils.getCanonicalName(String.class)); // java.lang.String
        System.out.println(ClassUtils.getPackageName(String.class)); // java.lang
        System.out.println(ClassUtils.getAbbreviatedName(String.class, 10)); // j.l.String
        System.out.println(ClassUtils.getAbbreviatedName(String.class, 15)); // j.lang.String
        System.out.println(ClassUtils.getAbbreviatedName(String.class, 20)); // java.lang.String
        System.out.println(ClassUtils.getCanonicalName(String.class)); // java.lang.String
        System.out.println(ClassUtils.getShortCanonicalName(String.class)); // String

        System.out.println(ClassUtils.isPrimitiveWrapper(int.class)); // false
        System.out.println(ClassUtils.isPrimitiveWrapper(Integer.class)); // true
        System.out.println(ClassUtils.isPrimitiveOrWrapper(int.class)); // true
        System.out.println(ClassUtils.isPrimitiveOrWrapper(Integer.class)); // true

        // [class java.lang.Object]
        System.out.println(ClassUtils.getAllSuperclasses(String.class));
        // [interface java.io.Serializable, interface java.lang.Comparable, interface java.lang.CharSequence]
        System.out.println(ClassUtils.getAllInterfaces(String.class));

        System.out.println(ClassUtils.convertClassesToClassNames(Arrays.asList(
                String.class, Integer.class, Character.class
        )));
        System.out.println(ClassUtils.convertClassNamesToClasses(Arrays.asList(
                "java.lang.String", "java.lang.Integer", "java.lang.Character"
        )));

        System.out.println(ClassUtils.isAssignable(int.class, Integer.class));
        System.out.println(ClassUtils.isAssignable(Integer.class, int.class));
        System.out.println(ClassUtils.isAssignable(Integer.class, char.class));
        System.out.println(ClassUtils.isAssignable(
                new Class[]{Integer.class, Boolean.class},
                new Class[]{int.class, boolean.class}
        )); // true
        System.out.println(ClassUtils.isAssignable(
                new Class[]{Integer.class, Boolean.class},
                new Class[]{int.class}
        )); // false

        // [class java.lang.Integer, class java.lang.Boolean]
        System.out.println(Arrays.toString(
                ClassUtils.primitivesToWrappers(int.class, boolean.class)));
        // [int,boolean]
        System.out.println(Arrays.toString(
                ClassUtils.wrappersToPrimitives(Integer.class, Boolean.class)));

        System.out.println(ClassUtils.isInnerClass(String.class));

        System.out.println(ClassUtils.getClass("java.lang.String"));
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(ClassUtils.getClass(classLoader, "java.lang.Integer"));
        System.out.println(ClassUtils.getClass(classLoader, "java.lang.Integer", true));

        System.out.println(ClassUtils.INNER_CLASS_SEPARATOR_CHAR);
        System.out.println(ClassUtils.INNER_CLASS_SEPARATOR);
        System.out.println(ClassUtils.PACKAGE_SEPARATOR_CHAR);
        System.out.println(ClassUtils.PACKAGE_SEPARATOR);
    }
}
