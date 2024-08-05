package com.denglitong.category_articles_backend.repository;

import com.denglitong.category_articles_backend.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE ArticleEntity a SET " +
            "a.title = :title, " +
            "a.content = :content," +
            "a.updatedTime = :updatedTime " +
            "WHERE a.articleId = :articleId")
    void updateArticleTitleContent(@Param("articleId") Integer articleId,
                                   @Param("title") String title,
                                   @Param("content") String content,
                                   @Param("updatedTime") LocalDateTime updatedTime);

    // TODO only return last 100 item with columns (articleId,title,updatedTime)
    @Query("SELECT a FROM ArticleEntity a " +
            "WHERE a.categoryId = :categoryId " +
            "ORDER BY a.updatedTime DESC")
    List<ArticleEntity> findByCategoryIdOrderByUpdatedTimeDesc(@Param("categoryId") Integer categoryId);
}
