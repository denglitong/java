package com.denglitong.category_articles_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
@Component
public class AppConfig {

    @Value("${user.login.keepDays:30}")
    private Integer daysKeepUserLogin;

    public Integer getDaysKeepUserLogin() {
        return daysKeepUserLogin;
    }
}
