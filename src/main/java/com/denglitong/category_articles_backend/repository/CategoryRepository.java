package com.denglitong.category_articles_backend.repository;

import com.denglitong.category_articles_backend.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    CategoryEntity findByCategoryName(String categoryName);

    @Modifying
    @Transactional
    @Query("UPDATE CategoryEntity c SET " +
            "c.categoryName = :categoryName, " +
            "c.updatedTime = :updatedTime " +
            "WHERE c.categoryId = :categoryId")
    void updateCategoryNameById(@Param("categoryId") Integer categoryId,
                                @Param("categoryName") String categoryName,
                                @Param("updatedTime") LocalDateTime updatedTime);

}
