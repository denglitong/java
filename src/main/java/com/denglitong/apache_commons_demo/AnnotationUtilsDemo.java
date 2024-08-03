package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.AnnotationUtils;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/14
 */
public class AnnotationUtilsDemo {

    public static void main(String[] args) {
        System.out.println(AnnotationUtils.isValidAnnotationMemberType(String.class));
        System.out.println(AnnotationUtils.isValidAnnotationMemberType(Class.class));
        System.out.println(AnnotationUtils.isValidAnnotationMemberType(int.class));
        System.out.println(AnnotationUtils.isValidAnnotationMemberType(double.class));
        System.out.println(AnnotationUtils.isValidAnnotationMemberType(EnumType.class));
    }

    private enum EnumType {
        HELLO
    }

}


