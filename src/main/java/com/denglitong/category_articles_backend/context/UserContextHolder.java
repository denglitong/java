package com.denglitong.category_articles_backend.context;

import org.springframework.util.Assert;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static void setContext(UserContext context) {
        Assert.notNull(context, "Only not-null UserContext instance are permitted");
        userContext.set(context);
    }

    public static UserContext getContext() {
        if (userContext.get() == null) {
            setContext(createEmptyContext());
        }
        return userContext.get();
    }

    public static void expire() {
        setContext(createEmptyContext());
    }

    private static UserContext createEmptyContext() {
        return new UserContext();
    }

}
