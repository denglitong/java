package com.denglitong.mybatis_plus.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 邓礼桐（deng.litong@foxmail.com）
 * @date 2021/4/2
 */
public class JSONUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JSONUtil() {
    }

    public static String generate(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T parse(String content, Class<T> valueType) throws JsonProcessingException {
        return MAPPER.readValue(content, valueType);
    }
}
