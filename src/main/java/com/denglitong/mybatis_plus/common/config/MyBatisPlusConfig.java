package com.denglitong.mybatis_plus.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 邓礼桐（deng.litong@foxmail.com）
 * @date 2021/4/2
 */
@Configuration
@MapperScan({"com.denglitong.mybatis_plus.*.mapper"})
public class MyBatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }

    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        // 定义Sequence主键生成策略
        // return properties -> properties.getGlobalConfig().getDbConfig().setKeyGenerator(new OracleKeyGenerator());

        // 定义ID生成器
        return properties -> properties.getGlobalConfig().setIdentifierGenerator(new CustomIdGenerator());
    }

    /**
     * 序列化枚举值为数据库存储值
     *
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }

    /**
     * @author 邓礼桐（deng.litong@foxmail.com）
     * @date 2021/4/3
     */
    @Component
    public static class CustomIdGenerator implements IdentifierGenerator {

        private static final Logger LOGGER = LoggerFactory.getLogger(CustomIdGenerator.class);

        private final AtomicLong atomicLong = new AtomicLong(1);

        @Override
        public Number nextId(Object entity) {
            // 可以将当前传入的class全类名来作为bizkey，进行分布式id调用生成
            String bizKey = entity.getClass().getName();
            LOGGER.info("bizKey:{}", bizKey);
            MetaObject metaObject = SystemMetaObject.forObject(entity);
            String name = (String)metaObject.getValue("name");
            final long id = atomicLong.getAndIncrement();
            LOGGER.info("为{}生成主键值->:{}", name, id);
            return id;
        }

    }
}