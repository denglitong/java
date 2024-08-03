package com.denglitong.apache_commons_demo.math;

import org.apache.commons.lang3.math.IEEE754rUtils;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/26
 */
public class IEEE754rUtilsDemo {

    public static void main(String[] args) {
        System.out.println(IEEE754rUtils.min(1f / 3, 2f / 5, 3f / 7, 4f / 7, 5f / 9));
        System.out.println(IEEE754rUtils.min(1d / 3, 2d / 5, 3d / 7, 4d / 7, 5d / 9));

        System.out.println(IEEE754rUtils.max(3.14, 5.26, 12.56));
    }
}
