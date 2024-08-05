package com.denglitong.category_articles_backend.auth;

import com.denglitong.category_articles_backend.context.UserContextHolder;
import com.denglitong.category_articles_backend.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
@Component
public class AuthRequiredInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        AuthRequired authRequired = ((HandlerMethod)handler).getMethodAnnotation(AuthRequired.class);
        if (authRequired == null) {
            authRequired = ((HandlerMethod)handler).getMethod().getDeclaringClass().getAnnotation(AuthRequired.class);
        }
        if (authRequired != null && !UserContextHolder.getContext().getActive()) {
            throw new BusinessException(HttpStatus.PROXY_AUTHENTICATION_REQUIRED.value(), authRequired.message());
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
