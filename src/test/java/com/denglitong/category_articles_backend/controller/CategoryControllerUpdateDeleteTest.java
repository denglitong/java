package com.denglitong.category_articles_backend.controller;

import com.denglitong.category_articles_backend.Application;
import com.denglitong.category_articles_backend.DTO.CategoryDTO;
import com.denglitong.category_articles_backend.DTO.ResponseDTO;
import com.denglitong.category_articles_backend.DTO.UserLoginDTO;
import com.denglitong.category_articles_backend.entity.CategoryEntity;
import com.denglitong.category_articles_backend.util.OrderAnnotation;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.OrderWith;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static com.denglitong.category_articles_backend.context.UserContext.USER_SESSION_ID;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpMethod.*;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:/category_articles_backend/application.yml")
@OrderWith(OrderAnnotation.class)
public class CategoryControllerUpdateDeleteTest extends BaseControllerTest {

    private static HttpHeaders headers;
    private static CategoryDTO firstLevelCategoryDTO;
    private static CategoryDTO secondLevelCategoryDTO;

    @PostConstruct
    public void init() {
        super.init();
        headers = new HttpHeaders();
    }

    @Test
    @Order(10)
    public void updateCategory_Auth() throws Exception {
        CategoryDTO dto = new CategoryDTO();
        ResponseDTO<?> responseDTO = updateCategory_Basic(dto, null);
        Assert.assertEquals(HttpStatus.PROXY_AUTHENTICATION_REQUIRED.value(), (int)responseDTO.getStatus());
        Assert.assertEquals("User authenticated fail, please login first", responseDTO.getMessage());
    }

    @Test
    @Order(20)
    public void login() throws Exception {
        initSampleUsers();
        UserLoginDTO loginDTO = new UserLoginDTO().withUserName("jackchen").withPassword("Aa@12345");
        ResponseDTO<?> responseDTO = loginUser_ValidateOK_Basic(loginDTO);
        assertNotNull(responseDTO.getData());
        loginDTO = buildDataTo(responseDTO, UserLoginDTO.class);
        assertNotNull(loginDTO);
        assertNotNull(loginDTO.getSessionId());
        headers.add(USER_SESSION_ID, loginDTO.getSessionId());
    }

    @Test
    @Order(21)
    public void createCategory_FirstLevel_ForUpdate() throws Exception {
        if (StringUtils.isBlank(headers.getFirst(USER_SESSION_ID))) {
            login();
        }
        CategoryDTO categoryDTO = new CategoryDTO().withCategoryName("Top-" + (System.currentTimeMillis() % 10000000));
        ResponseDTO<?> responseDTO = createCategory_OK_Basic(categoryDTO, headers);
        assertNotNull(responseDTO.getData());
        categoryDTO = buildDataTo(responseDTO, CategoryDTO.class);
        assertNotNull(categoryDTO);
        assertNotNull(categoryDTO.getCategoryId());
        assertNotNull(categoryDTO.getParentId());
        assertNotNull(categoryDTO.getCreatedTime());
        Assert.assertEquals(CategoryEntity.FIRST_LEVEL_PARENT_ID, categoryDTO.getParentId());
        firstLevelCategoryDTO = categoryDTO;
    }

    @Test
    @Order(30)
    public void updateCategory_CategoryName_Blank() throws Exception {
        if (firstLevelCategoryDTO == null) {
            createCategory_FirstLevel_ForUpdate();
        }
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(firstLevelCategoryDTO, dto);
        String[] blankNames = new String[]{null, "", "   "};
        for (String categoryName : blankNames) {
            dto.withCategoryName(categoryName);
            ResponseDTO<?> responseDTO = updateCategory_Fail_Basic(dto, headers);
            Assert.assertEquals("Validation fail: categoryName: category name can not be blank",
                    responseDTO.getMessage());
        }
    }

    @Test
    @Order(40)
    public void updateCategory_CategoryId_Null() throws Exception {
        if (firstLevelCategoryDTO == null) {
            createCategory_FirstLevel_ForUpdate();
        }
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(firstLevelCategoryDTO, dto);
        dto.setCategoryId(null);
        ResponseDTO<?> responseDTO = updateCategory_Fail_Basic(dto, headers);
        Assert.assertEquals("Validation fail: categoryId: category id can not be null",
                responseDTO.getMessage());
    }

    @Test
    @Order(40)
    public void updateCategory_CategoryId_NotExist() throws Exception {
        if (firstLevelCategoryDTO == null) {
            createCategory_FirstLevel_ForUpdate();
        }
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(firstLevelCategoryDTO, dto);
        Integer[] categoryIdNotExist = new Integer[]{10086, 10087, 10088};
        for (Integer categoryId : categoryIdNotExist) {
            dto.setCategoryId(categoryId);
            ResponseDTO<?> responseDTO = updateCategory_Fail_Basic(dto, headers);
            Assert.assertEquals("Validation fail: categoryId: categoryId value doesn't exist",
                    responseDTO.getMessage());
        }
    }

    @Test
    @Order(50)
    public void updateCategory_FirstLevel_Valid() throws Exception {
        if (firstLevelCategoryDTO == null) {
            createCategory_FirstLevel_ForUpdate();
        }
        firstLevelCategoryDTO.withCategoryName(firstLevelCategoryDTO.getCategoryName() + "_update_1");
        ResponseDTO<?> responseDTO = updateCategory_OK_Basic(firstLevelCategoryDTO, headers);

        CategoryDTO dto = buildDataTo(responseDTO, CategoryDTO.class);
        assertNotNull(dto);
        assertNotNull(dto.getCategoryId());
        assertNotNull(dto.getCreatedTime());
        Assert.assertEquals(firstLevelCategoryDTO.getCategoryName(), dto.getCategoryName());
    }

    @Test
    @Order(60)
    public void createCategory_SecondLevel_ForUpdate() throws Exception {
        if (firstLevelCategoryDTO == null) {
            createCategory_FirstLevel_ForUpdate();
        }
        CategoryDTO categoryDTO = new CategoryDTO()
                .withParentId(firstLevelCategoryDTO.getCategoryId())
                .withCategoryName(firstLevelCategoryDTO.getCategoryName() + "_sub_1");
        ResponseDTO<?> responseDTO = createCategory_OK_Basic(categoryDTO, headers);
        assertNotNull(responseDTO.getData());
        categoryDTO = buildDataTo(responseDTO, CategoryDTO.class);
        assertNotNull(categoryDTO);
        assertNotNull(categoryDTO.getCategoryId());
        assertNotNull(categoryDTO.getParentId());
        assertNotNull(categoryDTO.getCreatedTime());
        Assert.assertEquals(firstLevelCategoryDTO.getCategoryId(), categoryDTO.getParentId());
        secondLevelCategoryDTO = categoryDTO;
    }

    @Test
    @Order(70)
    public void updateCategory_SecondLevel_Valid() throws Exception {
        if (secondLevelCategoryDTO == null) {
            createCategory_SecondLevel_ForUpdate();
        }
        secondLevelCategoryDTO.setCategoryName(secondLevelCategoryDTO.getCategoryName() + "_update_1");
        ResponseDTO<?> responseDTO = createCategory_OK_Basic(secondLevelCategoryDTO, headers);
        assertNotNull(responseDTO.getData());
        CategoryDTO dto = buildDataTo(responseDTO, CategoryDTO.class);
        assertNotNull(dto);
        assertNotNull(dto.getCategoryId());
        assertNotNull(dto.getCreatedTime());
        Assert.assertEquals(secondLevelCategoryDTO.getCategoryName(), dto.getCategoryName());
    }

    @Test
    @Order(80)
    public void delete_CategoryId_Null() throws Exception {
        if (firstLevelCategoryDTO == null) {
            createCategory_FirstLevel_ForUpdate();
        }
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(firstLevelCategoryDTO, dto);
        dto.setCategoryId(null);
        ResponseDTO<?> responseDTO = deleteCategory_Fail_Basic(dto, headers);
        Assert.assertEquals("Validation fail: categoryId: category id can not be null",
                responseDTO.getMessage());
    }

    @Test
    @Order(100)
    public void delete_CategoryId_Valid() throws Exception {
        if (firstLevelCategoryDTO == null) {
            createCategory_FirstLevel_ForUpdate();
        }
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(firstLevelCategoryDTO, dto);
        deleteCategory_OK_Basic(dto, headers);

        // validate deletion
        ResponseDTO<?> responseDTO = invoke(GET, "/category/" + firstLevelCategoryDTO.getCategoryId(), dto, headers);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), (int)responseDTO.getStatus());
        Assert.assertEquals("getCategory.categoryId: categoryId doesn't exist", responseDTO.getMessage());
    }

    private ResponseDTO<?> updateCategory_Basic(CategoryDTO dto, HttpHeaders headers) throws Exception {
        ResponseDTO<?> responseDTO = invoke(PUT, "/category", dto, headers);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        return responseDTO;
    }

    private ResponseDTO<?> updateCategory_Fail_Basic(CategoryDTO dto, HttpHeaders headers) throws Exception {
        ResponseDTO<?> responseDTO = invoke(PUT, "/category", dto, headers);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), (int)responseDTO.getStatus());
        return responseDTO;
    }

    private ResponseDTO<?> updateCategory_OK_Basic(CategoryDTO dto, HttpHeaders headers) throws Exception {
        ResponseDTO<?> responseDTO = invoke(PUT, "/category", dto, headers);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        Assert.assertEquals(HttpStatus.OK.value(), (int)responseDTO.getStatus());
        return responseDTO;
    }

    private ResponseDTO<?> deleteCategory_Fail_Basic(CategoryDTO dto, HttpHeaders headers) throws Exception {
        ResponseDTO<?> responseDTO = invoke(DELETE, "/category", dto, headers);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), (int)responseDTO.getStatus());
        return responseDTO;
    }

    private ResponseDTO<?> deleteCategory_OK_Basic(CategoryDTO dto, HttpHeaders headers) throws Exception {
        ResponseDTO<?> responseDTO = invoke(DELETE, "/category", dto, headers);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        Assert.assertEquals(HttpStatus.OK.value(), (int)responseDTO.getStatus());
        return responseDTO;
    }
}
