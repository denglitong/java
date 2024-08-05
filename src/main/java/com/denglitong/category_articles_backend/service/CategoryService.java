package com.denglitong.category_articles_backend.service;

import com.denglitong.category_articles_backend.DTO.CategoryDTO;
import com.denglitong.category_articles_backend.context.UserContextHolder;
import com.denglitong.category_articles_backend.entity.CategoryEntity;
import com.denglitong.category_articles_backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.denglitong.category_articles_backend.utils.EntityDTOBuilder.buildDTO;
import static com.denglitong.category_articles_backend.utils.EntityDTOBuilder.buildEntity;
import static java.time.LocalDateTime.now;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO getByCategoryId(Integer categoryId) {
        return buildDTO(categoryRepository.findById(categoryId).get(), CategoryDTO.class);
    }

    public Optional<CategoryEntity> findByCategoryId(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public boolean existByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName) != null;
    }

    public boolean existByCategoryId(Integer categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    public CategoryDTO save(CategoryDTO categoryDTO) {
        categoryDTO.setUserId(UserContextHolder.getContext().getUserId());
        CategoryEntity category = buildEntity(categoryDTO, CategoryEntity.class);
        categoryRepository.save(category);
        return buildDTO(category, CategoryDTO.class);
    }

    public List<CategoryDTO> listAll() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        if (CollectionUtils.isEmpty(categoryEntities)) {
            return Collections.emptyList();
        }

        List<CategoryDTO> categories = buildDTO(categoryEntities, CategoryDTO.class);

        List<CategoryDTO> firstLevelCategories = categories.stream()
                .filter(c -> c.getParentId() == 0)
                .collect(Collectors.toList());
        Map<Integer, List<CategoryDTO>> subCategoriesMap = categories.stream()
                .filter(c -> c.getParentId() > 0)
                .collect(Collectors.groupingBy(CategoryDTO::getParentId));

        for (CategoryDTO firstLevelCategory : firstLevelCategories) {
            firstLevelCategory.setSubCategories(subCategoriesMap.getOrDefault(firstLevelCategory.getCategoryId(),
                    Collections.emptyList()));
        }
        return firstLevelCategories;
    }

    public CategoryDTO update(CategoryDTO dto) {
        categoryRepository.updateCategoryNameById(dto.getCategoryId(), dto.getCategoryName(), now());
        return buildDTO(categoryRepository.findById(dto.getCategoryId()).get(), CategoryDTO.class);
    }

    // TODO: soft delete
    public void delete(CategoryDTO dto) {
        if (categoryRepository.existsById(dto.getCategoryId())) {
            categoryRepository.deleteById(dto.getCategoryId());
        }
    }
}
