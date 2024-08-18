package com.denglitong.category_articles_backend.controller;

import com.denglitong.category_articles_backend.CategoryArticlesBackendApplication;
import com.denglitong.category_articles_backend.DTO.CategoryDTO;
import com.denglitong.category_articles_backend.DTO.ResponseDTO;
import com.denglitong.category_articles_backend.DTO.UserDTO;
import com.denglitong.category_articles_backend.DTO.UserLoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

import static com.fasterxml.jackson.databind.MapperFeature.USE_ANNOTATIONS;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CategoryArticlesBackendApplication.class)
@ActiveProfiles("category_articles_backend")
public abstract class BaseControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseControllerTest.class);

    protected static final String restContextPath = "/rest";

    @Autowired
    protected WebApplicationContext webAppContext;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @PostConstruct
    public void init() {
        // ignore @JsonIgnoreProperties to include all fields for serialization
        objectMapper.configure(USE_ANNOTATIONS, false);
    }

    @Before
    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    public ResponseDTO<?> post(String uri, Object data) throws Exception {
        return post(uri, data, null);
    }

    public ResponseDTO<?> invoke(HttpMethod httpMethod, String uri, Object data, HttpHeaders headers) throws Exception {
        uri = restContextPath + uri;
        MockHttpServletRequestBuilder builder = null;
        switch (httpMethod) {
            case POST:
                builder = MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON);
                break;
            case PUT:
                builder = MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON);
                break;
            case DELETE:
                builder = MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON);
                break;
            case GET:
                builder = MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON);
                break;
            default:
                throw new RuntimeException(String.format("httpMethod %s not supported", httpMethod.toString()));
        }

        if (data != null) {
            builder.content(objectMapper.writeValueAsString(data));
        }
        if (headers != null) {
            builder.headers(headers);
        }

        MvcResult result = mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andReturn();
        ResponseDTO<?> response = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseDTO.class);
        logger.info("uri {}, data: {}, headers: {} response: {}, content: {}", uri, data, headers, response,
                objectMapper.writeValueAsString(data));
        return response;
    }

    public ResponseDTO<?> post(String uri, Object data, HttpHeaders headers) throws Exception {
        return invoke(HttpMethod.POST, uri, data, headers);
    }

    public <T> T buildDataTo(ResponseDTO<?> response, Class<T> clazz) throws Exception {
        return objectMapper.readValue(objectMapper.writeValueAsString(response.getData()), clazz);
    }

    protected void initSampleUsers() {
        String[] userNameBlank = new String[]{"jackchen", "jackchen001", "jackchen002"};
        for (String userName : userNameBlank) {
            UserDTO userDTO = new UserDTO()
                    .withUserName(userName)
                    .withEmail(userName + "@qq.com")
                    .withPassword("Aa@12345");
            try {
                post("/user", userDTO);
            } catch (Exception e) {
                System.out.println("initUserNameSave: " + e.getMessage());
            }
        }
    }

    protected ResponseDTO<?> loginUser_ValidateOK_Basic(UserLoginDTO loginDTO) throws Exception {
        return loginUser_ValidateOK_Basic(loginDTO, null);
    }

    protected ResponseDTO<?> loginUser_ValidateOK_Basic(UserLoginDTO loginDTO, HttpHeaders headers) throws Exception {
        ResponseDTO<?> responseDTO = post("/user/login", loginDTO, headers);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        Assert.assertEquals(HttpStatus.OK.value(), (int)responseDTO.getStatus());
        return responseDTO;
    }

    protected ResponseDTO<?> logoutUser_ValidateOK_Basic(HttpHeaders headers) throws Exception {
        ResponseDTO<?> responseDTO = post("/user/logout", null, headers);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        Assert.assertEquals(HttpStatus.OK.value(), (int)responseDTO.getStatus());
        return responseDTO;
    }

    protected ResponseDTO<?> createCategory_OK_Basic(CategoryDTO dto, HttpHeaders headers) throws Exception {
        ResponseDTO<?> responseDTO = post("/category", dto, headers);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        Assert.assertEquals(HttpStatus.OK.value(), (int)responseDTO.getStatus());
        return responseDTO;
    }

}
