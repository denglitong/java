package com.denglitong.apache_commons_demo.reflect;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * see also FieldUtils, MethodUtils, TypeUtils
 *
 * @author litong.deng@foxmail.com
 * @date 2021/8/26
 */
public class ConstructorUtilsDemo {

    public static void main(String[] args)
            throws InvocationTargetException,
            NoSuchMethodException,
            IllegalAccessException,
            InstantiationException {
        // java: 无法将类 SimplePrivate 中的构造器 Simple应用到给定类型;
        // SimplePrivate simple = new SimplePrivate(1, "leon", 17);
        // System.out.println(simple);

        // No such accessible constructor on object: SimplePrivate
        // SimplePrivate simple1 = ConstructorUtils.invokeConstructor(SimplePrivate.class, 1, "leon", 17);
        // System.out.println(simple1);

        SimplePublic simplePublic = new SimplePublic(1, "leon", 17);
        // SimplePublic[age=17,name=leon,number=1]
        System.out.println(simplePublic);

        SimplePublic simplePublic1 = ConstructorUtils.invokeConstructor(SimplePublic.class, 1, "leon", 17);
        // SimplePublic[age=17,name=leon,number=1]
        System.out.println(simplePublic1);
    }

}
