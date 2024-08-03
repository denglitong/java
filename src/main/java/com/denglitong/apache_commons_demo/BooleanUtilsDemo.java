package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.BooleanUtils;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/14
 */
public class BooleanUtilsDemo {

    public static void main(String[] args) {
        System.out.println(BooleanUtils.isTrue(true));
        System.out.println(BooleanUtils.isNotTrue(false));
        System.out.println(BooleanUtils.negate(true)); // false

        boolean[] conditions = new boolean[]{true, false};
        System.out.println(BooleanUtils.and(conditions));
        System.out.println(BooleanUtils.or(conditions));
        System.out.println(BooleanUtils.xor(conditions));

        System.out.println(BooleanUtils.toInteger(true));
        System.out.println(BooleanUtils.toInteger(false));

        System.out.println(BooleanUtils.toStringYesNo(true));
        System.out.println(BooleanUtils.toStringYesNo(false));
        System.out.println(BooleanUtils.toStringYesNo(null));

        System.out.println(BooleanUtils.toStringOnOff(true));
        System.out.println(BooleanUtils.toStringOnOff(false));
        System.out.println(BooleanUtils.toStringOnOff(null));

        System.out.println(BooleanUtils.toStringTrueFalse(true));
        System.out.println(BooleanUtils.toStringTrueFalse(false));
        System.out.println(BooleanUtils.toStringTrueFalse(null));

        System.out.println(BooleanUtils.TRUE);
        System.out.println(BooleanUtils.FALSE);
        System.out.println(BooleanUtils.ON);
        System.out.println(BooleanUtils.OFF);
        System.out.println(BooleanUtils.YES);
        System.out.println(BooleanUtils.NO);
    }
}
