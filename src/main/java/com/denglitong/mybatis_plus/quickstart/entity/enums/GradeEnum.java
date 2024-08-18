package com.denglitong.mybatis_plus.quickstart.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

/**
 * @author denglitong
 * @date 2021/4/4
 */
public enum GradeEnum {
    PRIMARY(1, "小学"),
    SECONDORY(2, "中学"),
    HIGH(3, "高中");

    /**
     * 前端传值反序列化为 enum 时 code 需传 string，如果传 int 则为枚举值索引，为何？
     *
     * @param code
     * @param desc
     */
    GradeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    private final Integer code;

    private final String desc;

    /**
     * 序列化返回的属性值
     *
     * @return
     */
    @JsonValue
    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 从属性值反序列化
     *
     * @param code
     * @return
     */
    @JsonCreator
    public static GradeEnum getByCode(Integer code) {
        return Stream.of(values()).filter(x -> x.getCode().equals(code)).findFirst().orElse(null);
    }
}
