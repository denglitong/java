package com.denglitong.category_articles_backend.DTO;

import com.denglitong.category_articles_backend.entity.CategoryEntity;
import com.denglitong.category_articles_backend.validate.ExistValue;
import com.denglitong.category_articles_backend.validate.UniqueValue;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
public class CategoryDTO {

    @NotNull(message = "category id can not be null",
            groups = {UPDATE.CATEGORY_ID.class, UPDATE.NOT_NULL.class, DELETE.class})
    @ExistValue(entityClass = CategoryEntity.class, column = CategoryEntity.CATEGORY_ID, message = "categoryId value doesn't exist",
            groups = {UPDATE.CATEGORY_ID.class, UPDATE.EXIST_VALUE.class})
    private Integer categoryId;

    @ExistValue(entityClass = CategoryEntity.class, column = CategoryEntity.PARENT_ID, message = "category parent id not exist",
            groups = SAVE.PARENT_ID.class)
    private Integer parentId = CategoryEntity.FIRST_LEVEL_PARENT_ID;

    private Integer userId;

    @NotBlank(message = "category name can not be blank",
            groups = {SAVE.CATEGORY_NAME.class, SAVE.NOT_BLANK.class,
                    UPDATE.CATEGORY_NAME.class, UPDATE.NOT_BLANK.class})
    @UniqueValue(entityClass = CategoryEntity.class, column = CategoryEntity.CATEGORY_NAME, message = "category name already exist",
            groups = {SAVE.CATEGORY_NAME.class, SAVE.UNIQUE_VALUE.class,
                    UPDATE.CATEGORY_NAME.class, UPDATE.UNIQUE_VALUE.class})
    private String categoryName;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private List<CategoryDTO> subCategories = Collections.emptyList();

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public List<CategoryDTO> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryDTO> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public CategoryDTO withCategoryId(Integer categoryId) {
        setCategoryId(categoryId);
        return this;
    }

    public CategoryDTO withParentId(Integer parentId) {
        setParentId(parentId);
        return this;
    }

    public CategoryDTO withCategoryName(String categoryName) {
        setCategoryName(categoryName);
        return this;
    }

    public CategoryDTO withUserId(Integer userId) {
        setUserId(userId);
        return this;
    }

    @GroupSequence({
            SAVE.PARENT_ID.class,
            SAVE.CATEGORY_NAME.class,
            SAVE.NOT_NULL.class,
            SAVE.NOT_BLANK.class,
            SAVE.EXIST_VALUE.class,
            SAVE.UNIQUE_VALUE.class,
    })
    public interface SAVE {
        interface PARENT_ID {}
        interface CATEGORY_NAME {}
        interface NOT_NULL {}
        interface NOT_BLANK {}
        interface EXIST_VALUE {}
        interface UNIQUE_VALUE {}
    }

    @GroupSequence({
            UPDATE.CATEGORY_ID.class,
            UPDATE.CATEGORY_NAME.class,
            UPDATE.NOT_NULL.class,
            UPDATE.NOT_BLANK.class,
            UPDATE.EXIST_VALUE.class,
            UPDATE.UNIQUE_VALUE.class,
    })
    public interface UPDATE {
        interface CATEGORY_ID {}
        interface CATEGORY_NAME {}
        interface NOT_NULL {}
        interface NOT_BLANK {}
        interface EXIST_VALUE {}
        interface UNIQUE_VALUE {}
    }

    public interface DELETE {}
}
