package com.denglitong.mybatis_plus;

import com.denglitong.category_articles_backend.CategoryArticlesBackendApplication;
import com.google.common.collect.Sets;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
@MapperScan({"com.denglitong.mybatis_plus.*.mapper"})
public class MybatisPlusApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CategoryArticlesBackendApplication.class);
		app.setDefaultProperties(Collections.singletonMap(
				"spring.config.location", "classpath:/mybatis_plus/application.yml"
		));
		app.run(args);
	}
}
