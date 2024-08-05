package com.denglitong.category_articles_backend.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
@Entity
@Table(name = "category", indexes = {
        @Index(columnList = "createdTime"),
        @Index(columnList = "parentId, createdTime")
})
public class CategoryEntity {

    public static final String CATEGORY_ID = "categoryId";
    public static final String PARENT_ID = "parentId";
    public static final String CATEGORY_NAME = "categoryName";
    public static final Integer FIRST_LEVEL_PARENT_ID = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer categoryId;

    @Column(columnDefinition = "integer default 0")
    private Integer parentId;

    @Column(nullable = true)
    private Integer userId;

    @Column(unique = true, nullable = false, columnDefinition = "text")
    private String categoryName;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

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
}
