package com.denglitong.category_articles_backend.auth;

import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface AuthRequired {

    String message() default "User authenticated fail, please login first";

    // 分组
    Class<?>[] groups() default {};

    // 负载
    Class<? extends Payload>[] payload() default {};
}
