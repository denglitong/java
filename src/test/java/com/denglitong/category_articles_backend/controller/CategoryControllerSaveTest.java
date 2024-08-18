package com.denglitong.category_articles_backend.controller;

import com.denglitong.category_articles_backend.CategoryArticlesBackendApplication;
import com.denglitong.category_articles_backend.DTO.CategoryDTO;
import com.denglitong.category_articles_backend.DTO.ResponseDTO;
import com.denglitong.category_articles_backend.DTO.UserLoginDTO;
import com.denglitong.category_articles_backend.util.OrderAnnotation;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.OrderWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static com.denglitong.category_articles_backend.context.UserContext.USER_SESSION_ID;
import static org.junit.Assert.assertNotNull;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CategoryArticlesBackendApplication.class)
@OrderWith(OrderAnnotation.class)
public class CategoryControllerSaveTest extends BaseControllerTest {

    private static HttpHeaders headers;
    private static Integer firstLevelCategoryId;
    private static String firstLevelCategoryName;
    private static Integer secondLevelCategoryId;
    private static String secondLevelCategoryName;

    @PostConstruct
    public void init() {
        super.init();
        headers = new HttpHeaders();
    }

    @Test
    @Order(10)
    public void saveCategory_Auth() throws Exception {
        CategoryDTO dto = new CategoryDTO();
        ResponseDTO<?> responseDTO = createCategory_Basic(dto, null);
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
    @Order(30)
    public void saveCategory_CategoryName_Blank() throws Exception {
        if (StringUtils.isBlank(headers.getFirst(USER_SESSION_ID))) {
            login();
        }
        String[] blankNames = new String[]{null, "", "   "};
        for (String categoryName : blankNames) {
            CategoryDTO dto = new CategoryDTO().withCategoryName(categoryName);
            ResponseDTO<?> responseDTO = createCategory_Fail_Basic(dto, headers);
            Assert.assertEquals("Validation fail: categoryName: category name can not be blank",
                    responseDTO.getMessage());
        }
    }

    @Test
    @Order(40)
    public void saveCategory_ParentId_Invalid() throws Exception {
        if (StringUtils.isBlank(headers.getFirst(USER_SESSION_ID))) {
            login();
        }
        Integer[] invalidParentId = new Integer[]{null, -1, 10086};
        for (Integer parentId : invalidParentId) {
            CategoryDTO dto = new CategoryDTO().withParentId(parentId).withCategoryName("TestCase-1");
            ResponseDTO<?> responseDTO = createCategory_Fail_Basic(dto, headers);
            Assert.assertEquals("Validation fail: parentId: category parent id not exist",
                    responseDTO.getMessage());
        }
    }

    @Test
    @Order(50)
    public void saveCategory_FirstLevel_Valid() throws Exception {
        if (StringUtils.isBlank(headers.getFirst(USER_SESSION_ID))) {
            login();
        }
        CategoryDTO categoryDTO = new CategoryDTO().withCategoryName("TestCase-1");
        ResponseDTO<?> responseDTO = createCategory_OK_Basic(categoryDTO, headers);

        CategoryDTO dto = buildDataTo(responseDTO, CategoryDTO.class);
        assertNotNull(dto);
        assertNotNull(dto.getCategoryId());
        assertNotNull(dto.getCreatedTime());
        Assert.assertEquals(categoryDTO.getCategoryName(), dto.getCategoryName());
        firstLevelCategoryId = dto.getCategoryId();
        firstLevelCategoryName = dto.getCategoryName();
    }

    @Test
    @Order(60)
    public void saveCategory_CategoryName_Exist() throws Exception {
        if (StringUtils.isBlank(firstLevelCategoryName)) {
            saveCategory_FirstLevel_Valid();
        }
        CategoryDTO categoryDTO = new CategoryDTO().withCategoryName(firstLevelCategoryName);
        ResponseDTO<?> responseDTO = createCategory_Fail_Basic(categoryDTO, headers);
        Assert.assertEquals("Validation fail: categoryName: category name already exist",
                responseDTO.getMessage());
    }

    @Test
    @Order(70)
    public void saveCategory_SecondLevel_ParentId_NotExist() throws Exception {
        if (StringUtils.isBlank(headers.getFirst(USER_SESSION_ID))) {
            login();
        }
        Integer[] notExistParentId = new Integer[]{10086, 10087, 10088};
        for (Integer parentId : notExistParentId) {
            CategoryDTO categoryDTO = new CategoryDTO()
                    .withParentId(parentId)
                    .withCategoryName(firstLevelCategoryName + "_" + parentId);
            ResponseDTO<?> responseDTO = createCategory_Fail_Basic(categoryDTO, headers);
            Assert.assertEquals("Validation fail: parentId: category parent id not exist",
                    responseDTO.getMessage());
        }
    }

    @Test
    @Order(80)
    public void saveCategory_SecondLevel_Valid() throws Exception {
        if (StringUtils.isBlank(firstLevelCategoryName)) {
            saveCategory_FirstLevel_Valid();
        }
        CategoryDTO categoryDTO = new CategoryDTO()
                .withParentId(firstLevelCategoryId)
                .withCategoryName(firstLevelCategoryName + "_1");
        ResponseDTO<?> responseDTO = createCategory_OK_Basic(categoryDTO, headers);

        CategoryDTO dto = buildDataTo(responseDTO, CategoryDTO.class);
        assertNotNull(dto);
        assertNotNull(dto.getCategoryId());
        assertNotNull(dto.getCreatedTime());
        Assert.assertEquals(categoryDTO.getCategoryName(), dto.getCategoryName());
        secondLevelCategoryId = dto.getCategoryId();
        secondLevelCategoryName = dto.getCategoryName();
    }

    private ResponseDTO<?> createCategory_Basic(CategoryDTO dto, HttpHeaders headers) throws Exception {
        ResponseDTO<?> responseDTO = post("/category", dto, headers);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        return responseDTO;
    }

    private ResponseDTO<?> createCategory_Fail_Basic(CategoryDTO dto, HttpHeaders headers) throws Exception {
        ResponseDTO<?> responseDTO = post("/category", dto, headers);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), (int)responseDTO.getStatus());
        return responseDTO;
    }

}
