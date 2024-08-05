package com.denglitong.category_articles_backend.validate;

import com.denglitong.category_articles_backend.entity.CategoryEntity;
import com.denglitong.category_articles_backend.entity.UserEntity;
import com.denglitong.category_articles_backend.service.CategoryService;
import com.denglitong.category_articles_backend.service.UserService;
import com.denglitong.category_articles_backend.utils.BeanUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
public class UniqueValueValidator implements ConstraintValidator<UniqueValue, String> {

    private Class<?> entityClass;
    private String column;

    private UserService userService;
    private CategoryService categoryService;

    @Override
    public void initialize(UniqueValue annotation) {
        entityClass = annotation.entityClass();
        column = annotation.column();
        userService = BeanUtil.getBean(UserService.class);
        categoryService = BeanUtil.getBean(CategoryService.class);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (UserEntity.class.equals(entityClass) && UserEntity.USER_NAME.equalsIgnoreCase(column)) {
            return !userService.existByUserName(value);
        }
        if (UserEntity.class.equals(entityClass) && UserEntity.EMAIL.equalsIgnoreCase(column)) {
            return !userService.existByEmail(value);
        }
        if (CategoryEntity.class.equals(entityClass) && CategoryEntity.CATEGORY_NAME.equalsIgnoreCase(column)) {
            return !categoryService.existByCategoryName(value);
        }
        throw new UnsupportedOperationException(String.format(
                "UniqueValue(entityClass = %s, column = %s) does not support", entityClass, column));
    }

}
