package com.denglitong.category_articles_backend;

import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Collections;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/27
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap(
                "spring.config.location", "classpath:/category_articles_backend/application.yml"
        ));
        String sqlitePath = new ClassPathResource("category_articles_backend/category_articles.db")
                .getFile().getAbsolutePath();
        app.setDefaultProperties(Collections.singletonMap(
                "spring.datasource.url", "jdbc:sqlite:" + sqlitePath
        ));
        app.run(args);
    }

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 校验快速失败
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
