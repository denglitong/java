package com.denglitong.category_articles_backend.validate;

import com.denglitong.category_articles_backend.entity.CategoryEntity;
import com.denglitong.category_articles_backend.service.CategoryService;
import com.denglitong.category_articles_backend.utils.BeanUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/30
 */
public class SecondLevelCategoryIdValidator implements ConstraintValidator<SecondLevelCategoryId, Integer> {

    private CategoryService categoryService;

    @Override
    public void initialize(SecondLevelCategoryId annotation) {
        categoryService = BeanUtil.getBean(CategoryService.class);
    }

    @Override
    public boolean isValid(Integer categoryId, ConstraintValidatorContext constraintValidatorContext) {
        if (categoryId == null || categoryId <= 0) {
            return false;
        }
        Optional<CategoryEntity> category = categoryService.findByCategoryId(categoryId);
        if (category.isEmpty() || category.get().getParentId() == 0) {
            return false;
        }
        return true;
    }
}
