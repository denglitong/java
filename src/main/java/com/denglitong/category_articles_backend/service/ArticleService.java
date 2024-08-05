package com.denglitong.category_articles_backend.service;

import com.denglitong.category_articles_backend.DTO.ArticleDTO;
import com.denglitong.category_articles_backend.context.UserContextHolder;
import com.denglitong.category_articles_backend.entity.ArticleEntity;
import com.denglitong.category_articles_backend.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.denglitong.category_articles_backend.utils.EntityDTOBuilder.buildDTO;
import static com.denglitong.category_articles_backend.utils.EntityDTOBuilder.buildEntity;


/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/30
 */
@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public boolean existByArticleId(Integer articleId) {
        return articleRepository.existsById(articleId);
    }

    public ArticleDTO save(ArticleDTO articleDTO) {
        articleDTO.setUserId(UserContextHolder.getContext().getUserId());
        ArticleEntity article = buildEntity(articleDTO, ArticleEntity.class);
        articleRepository.save(article);
        return buildDTO(article, ArticleDTO.class);
    }

    public ArticleDTO getByArticleId(Integer articleId) {
        Optional<ArticleEntity> article = articleRepository.findById(articleId);
        return article.map(entity -> buildDTO(entity, ArticleDTO.class)).orElse(null);
    }

    /**
     * TODO validate current user whether authorize to update article
     *
     * @param articleDTO
     * @return
     */
    public ArticleDTO update(ArticleDTO articleDTO) {
        articleDTO.setUpdatedTime(LocalDateTime.now());
        ArticleEntity article = buildEntity(articleDTO, ArticleEntity.class);
        articleRepository.updateArticleTitleContent(article.getArticleId(), article.getTitle(), article.getContent(),
                LocalDateTime.now());
        return buildDTO(articleRepository.findById(article.getArticleId()).get(), ArticleDTO.class);
    }

    // TODO: soft delete
    public void delete(ArticleDTO articleDTO) {
        if (articleRepository.existsById(articleDTO.getArticleId())) {
            articleRepository.deleteById(articleDTO.getArticleId());
        }
    }

    public List<ArticleDTO> listByCategoryId(Integer categoryId) {
        List<ArticleEntity> articleEntities = articleRepository.findByCategoryIdOrderByUpdatedTimeDesc(categoryId);
        return buildDTO(articleEntities, ArticleDTO.class);
    }
}
