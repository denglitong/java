package com.denglitong.category_articles_backend.controller;

import com.denglitong.category_articles_backend.DTO.CategoryDTO;
import com.denglitong.category_articles_backend.auth.AuthRequired;
import com.denglitong.category_articles_backend.entity.CategoryEntity;
import com.denglitong.category_articles_backend.service.CategoryService;
import com.denglitong.category_articles_backend.validate.ExistValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
@RestController
@RequestMapping("/rest/category")
@Validated
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 创建栏目
     *
     * @return
     */
    @PostMapping
    @AuthRequired
    public CategoryDTO save(@RequestBody @Validated(CategoryDTO.SAVE.class) CategoryDTO categoryDTO) {
        return categoryService.save(categoryDTO);
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO getCategory(
            @PathVariable("categoryId")
            @ExistValue(entityClass = CategoryEntity.class, column = CategoryEntity.CATEGORY_ID, message = "categoryId doesn't exist")
            Integer categoryId
    ) {
        return categoryService.getByCategoryId(categoryId);
    }

    @GetMapping("/list")
    public List<CategoryDTO> list() {
        return categoryService.listAll();
    }

    @PutMapping
    @AuthRequired
    public CategoryDTO update(@RequestBody @Validated(CategoryDTO.UPDATE.class) CategoryDTO categoryDTO) {
        return categoryService.update(categoryDTO);
    }

    @DeleteMapping
    @AuthRequired
    public void delete(@RequestBody @Validated(CategoryDTO.DELETE.class) CategoryDTO categoryDTO) {
        categoryService.delete(categoryDTO);
    }

}
