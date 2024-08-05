package com.denglitong.category_articles_backend.validate;

import com.denglitong.category_articles_backend.entity.ArticleEntity;
import com.denglitong.category_articles_backend.entity.CategoryEntity;
import com.denglitong.category_articles_backend.exception.BusinessException;
import com.denglitong.category_articles_backend.service.ArticleService;
import com.denglitong.category_articles_backend.service.CategoryService;
import com.denglitong.category_articles_backend.utils.BeanUtil;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.denglitong.category_articles_backend.entity.CategoryEntity.FIRST_LEVEL_PARENT_ID;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
public class ExistValueValidator implements ConstraintValidator<ExistValue, Object> {

    private Class<?> entityClass;
    private String column;

    private CategoryService categoryService;
    private ArticleService articleService;

    @Override
    public void initialize(ExistValue annotation) {
        entityClass = annotation.entityClass();
        column = annotation.column();
        categoryService = BeanUtil.getBean(CategoryService.class);
        articleService = BeanUtil.getBean(ArticleService.class);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        if (CategoryEntity.class.equals(entityClass) && CategoryEntity.CATEGORY_ID.equalsIgnoreCase(column)) {
            Integer categoryId = Integer.parseInt(value.toString());
            return categoryService.existByCategoryId(categoryId);
        }
        if (CategoryEntity.class.equals(entityClass) && CategoryEntity.PARENT_ID.equalsIgnoreCase(column)) {
            Integer parentCategoryId = Integer.parseInt(value.toString());
            return parentCategoryId.equals(CategoryEntity.FIRST_LEVEL_PARENT_ID) ||
                    categoryService.existByCategoryId(parentCategoryId);
        }
        if (ArticleEntity.class.equals(entityClass) && ArticleEntity.ARTICLE_ID.equalsIgnoreCase(column)) {
            Integer articleId = Integer.parseInt(value.toString());
            return articleService.existByArticleId(articleId);
        }
        throw new BusinessException(HttpStatus.NOT_IMPLEMENTED.value(), String.format(
                "ExistValue(entityClass = %s, column = %s) does not support", entityClass, column));
    }
}
