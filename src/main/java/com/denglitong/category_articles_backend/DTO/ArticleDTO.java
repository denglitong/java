package com.denglitong.category_articles_backend.DTO;


import com.denglitong.category_articles_backend.entity.ArticleEntity;
import com.denglitong.category_articles_backend.validate.ExistValue;
import com.denglitong.category_articles_backend.validate.SecondLevelCategoryId;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */

public class ArticleDTO {

    @NotNull(message = "articleId can not be null",
            groups = {UPDATE.ARTICLE_ID.class, UPDATE.NOT_BLANK.class, DELETE.class})
    @ExistValue(entityClass = ArticleEntity.class, column = ArticleEntity.ARTICLE_ID, message = "articleId doesn't exist",
            groups = {UPDATE.ARTICLE_ID.class, UPDATE.EXIST_VALUE.class})
    private Integer articleId;

    @NotBlank(message = "title can not be blank", groups = {SAVE.TITLE.class, UPDATE.TITLE.class})
    private String title;

    @NotBlank(message = "content can not be blank", groups = {SAVE.CONTENT.class, UPDATE.CONTENT.class})
    private String content;

    private Integer userId;

    @NotNull(message = "categoryId can not be null", groups = {SAVE.CATEGORY_ID.class, SAVE.NOT_NULL.class})
    @SecondLevelCategoryId(groups = {SAVE.CATEGORY_ID.class, SAVE.SECOND_LEVEL_CATEGORY_ID.class})
    private Integer categoryId;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    @GroupSequence({
            SAVE.TITLE.class,
            SAVE.CONTENT.class,
            SAVE.CATEGORY_ID.class,
            SAVE.NOT_NULL.class,
            SAVE.NOT_BLANK.class,
            SAVE.SECOND_LEVEL_CATEGORY_ID.class,
    })
    public interface SAVE {
        interface TITLE {}
        interface CONTENT {}
        interface CATEGORY_ID {}
        interface NOT_NULL {}
        interface NOT_BLANK {}
        interface SECOND_LEVEL_CATEGORY_ID {}
    }

    @GroupSequence({
            UPDATE.ARTICLE_ID.class,
            UPDATE.TITLE.class,
            UPDATE.CONTENT.class,
            UPDATE.NOT_NULL.class,
            UPDATE.NOT_BLANK.class,
            UPDATE.EXIST_VALUE.class,
    })
    public interface UPDATE {
        interface ARTICLE_ID {}
        interface TITLE {}
        interface CONTENT {}
        interface NOT_NULL {}
        interface NOT_BLANK {}
        interface EXIST_VALUE {}
    }

    public interface DELETE {}
}
