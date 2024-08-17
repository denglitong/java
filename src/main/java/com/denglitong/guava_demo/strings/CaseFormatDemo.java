package com.denglitong.guava_demo.strings;

import com.google.common.base.CaseFormat;

/**
 * 大小写格式转换
 * <p>
 * 驼峰
 * 下划线
 * 中划线
 *
 * @author litong.deng@foxmail.com
 * @date 2021/10/7
 */
public class CaseFormatDemo {

    public static void main(String[] args) {
        System.out.println(CaseFormat.UPPER_UNDERSCORE
                .to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME")); // constantName
    }
}
