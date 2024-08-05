package com.denglitong.category_articles_backend.validate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
public class AttrsNotAllBlankValidator implements ConstraintValidator<AttrsNotAllBlank, Object> {

    String[] attributes;

    @Override
    public void initialize(AttrsNotAllBlank annotation) {
        this.attributes = annotation.attributes();
    }

    @Override
    public boolean isValid(Object target, ConstraintValidatorContext constraintValidatorContext) {
        return !Arrays.stream(attributes).allMatch(attr -> {
            try {
                Object field = FieldUtils.readField(target, attr, true);
                return field == null || StringUtils.isBlank(field.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        });
    }
}
