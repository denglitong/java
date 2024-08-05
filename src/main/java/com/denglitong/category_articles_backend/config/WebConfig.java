package com.denglitong.category_articles_backend.config;

import com.denglitong.category_articles_backend.auth.AuthRequiredInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private AuthRequiredInterceptor authRequiredInterceptor;

    @Autowired
    public void setAuthRequiredInterceptor(AuthRequiredInterceptor authRequiredInterceptor) {
        this.authRequiredInterceptor = authRequiredInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authRequiredInterceptor)
                .addPathPatterns("/rest/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
