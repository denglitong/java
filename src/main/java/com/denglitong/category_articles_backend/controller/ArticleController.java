package com.denglitong.category_articles_backend.controller;

import com.denglitong.category_articles_backend.DTO.ArticleDTO;
import com.denglitong.category_articles_backend.auth.AuthRequired;
import com.denglitong.category_articles_backend.entity.ArticleEntity;
import com.denglitong.category_articles_backend.service.ArticleService;
import com.denglitong.category_articles_backend.validate.ExistValue;
import com.denglitong.category_articles_backend.validate.SecondLevelCategoryId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
@RestController
@RequestMapping("/rest/article")
@Validated
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    @AuthRequired
    public ArticleDTO save(@RequestBody @Validated(ArticleDTO.SAVE.class) ArticleDTO articleDTO) {
        return articleService.save(articleDTO);
    }

    @GetMapping("/{articleId}")
    public ArticleDTO getArticle(
            @PathVariable("articleId")
            @ExistValue(entityClass = ArticleEntity.class, column = ArticleEntity.ARTICLE_ID, message = "articleId not exist")
            Integer articleId
    ) {
        return articleService.getByArticleId(articleId);
    }

    @PutMapping
    @AuthRequired
    public ArticleDTO update(@RequestBody @Validated(ArticleDTO.UPDATE.class) ArticleDTO articleDTO) {
        return articleService.update(articleDTO);
    }

    @DeleteMapping
    @AuthRequired
    public void delete(@RequestBody @Validated(ArticleDTO.DELETE.class) ArticleDTO articleDTO) {
        articleService.delete(articleDTO);
    }

    @GetMapping("/category/{categoryId}")
    public List<ArticleDTO> listSecondLevelCategoryArticles(
            @PathVariable("categoryId")
            @SecondLevelCategoryId Integer categoryId
    ) {
        return articleService.listByCategoryId(categoryId);
    }
}
