package com.denglitong.category_articles_backend.controller;

import com.denglitong.category_articles_backend.CategoryArticlesBackendApplication;
import com.denglitong.category_articles_backend.DTO.ResponseDTO;
import com.denglitong.category_articles_backend.DTO.UserLoginDTO;
import com.denglitong.category_articles_backend.context.UserContext;
import com.denglitong.category_articles_backend.context.UserContextHolder;
import com.denglitong.category_articles_backend.repository.SessionRepository;
import com.denglitong.category_articles_backend.util.OrderAnnotation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.OrderWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CategoryArticlesBackendApplication.class)
@TestPropertySource(locations = "classpath:/category_articles_backend/application.yml")
@OrderWith(OrderAnnotation.class)
public class UserControllerLoginTest extends BaseControllerTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    @Order(10)
    public void login_UserName_And_Email_All_Blank() throws Exception {
        UserLoginDTO loginDTO = new UserLoginDTO().withPassword("A@123456");
        ResponseDTO<?> responseDTO = loginUser_ValidateFail_Basic(loginDTO);
        Assert.assertEquals(
                "Validation fail: userLoginDTO: userName and email can not be all blank",
                responseDTO.getMessage());
    }

    @Test
    @Order(20)
    public void login_Password_Blank() throws Exception {
        UserLoginDTO loginDTO = new UserLoginDTO().withUserName("jackchen");
        ResponseDTO<?> responseDTO = loginUser_ValidateFail_Basic(loginDTO);
        Assert.assertEquals("Validation fail: password: password can not be blank",
                responseDTO.getMessage());
    }

    @Test
    @Order(30)
    public void login_Auth_Rejected_Using_UserName() throws Exception {
        initSampleUsers();
        UserContextHolder.expire();
        for (int i = 0; i < 4; i++) {
            String userName = "Rand" + (System.currentTimeMillis() % 10000000);
            String password = "Aa@" + userName;
            UserLoginDTO loginDTO = new UserLoginDTO()
                    .withUserName(userName)
                    .withPassword(password);
            ResponseDTO<?> responseDTO = loginUser_ValidateFail_Basic(loginDTO, new HttpHeaders());
            Assert.assertEquals("userName or password wrong",
                    responseDTO.getMessage());
        }
    }

    @Test
    @Order(40)
    public void login_Auth_Rejected_Using_Email() throws Exception {
        initSampleUsers();
        UserContextHolder.expire();
        for (int i = 0; i < 4; i++) {
            String email = "rAnd" + (System.currentTimeMillis() % 10000000) + "@qq.com";
            String password = "Aa@" + email;
            UserLoginDTO loginDTO = new UserLoginDTO()
                    .withEmail(email)
                    .withPassword(password);
            ResponseDTO<?> responseDTO = loginUser_ValidateFail_Basic(loginDTO, new HttpHeaders());
            Assert.assertEquals("email or password wrong",
                    responseDTO.getMessage());
        }
    }

    @Test
    @Order(50)
    public void login_Auth_Pass_Using_UserName() throws Exception {
        initSampleUsers();

        List<List<String>> accountPair = Arrays.asList(
                Arrays.asList("jackchen", "Aa@12345"),
                Arrays.asList("jackchen001", "Aa@12345"),
                Arrays.asList("jackchen002", "Aa@12345"));
        for (List<String> account : accountPair) {
            UserLoginDTO loginDTO = new UserLoginDTO()
                    .withUserName(account.get(0))
                    .withPassword(account.get(1));
            ResponseDTO<?> responseDTO = loginUser_ValidateOK_Basic(loginDTO);

            UserLoginDTO data = buildDataTo(responseDTO, UserLoginDTO.class);
            assertNotNull(data);
            assertNotNull(data.getUserName());
            assertNotNull(data.getEmail());
            assertNotNull(data.getSessionId());
        }
    }

    @Test
    @Order(60)
    public void login_Auth_Pass_Using_Email() throws Exception {
        initSampleUsers();

        List<List<String>> accountPair = Arrays.asList(
                Arrays.asList("jackchen@qq.com", "Aa@12345"),
                Arrays.asList("jackchen001@qq.com", "Aa@12345"),
                Arrays.asList("jackchen002@qq.com", "Aa@12345"));
        for (List<String> account : accountPair) {
            UserLoginDTO loginDTO = new UserLoginDTO()
                    .withEmail(account.get(0))
                    .withPassword(account.get(1));
            ResponseDTO<?> responseDTO = loginUser_ValidateOK_Basic(loginDTO);

            UserLoginDTO data = buildDataTo(responseDTO, UserLoginDTO.class);
            assertNotNull(data);
            assertNotNull(data.getUserName());
            assertNotNull(data.getEmail());
            assertNotNull(data.getSessionId());
        }
    }

    /**
     * rememberMe can use sessionId store in chrom local storage to login again
     *
     * @throws Exception
     */
    @Test
    @Order(70)
    public void login_Auth_Pass_Using_SessionId() throws Exception {
        initSampleUsers();

        List<List<String>> accountPair = Arrays.asList(
                Arrays.asList("jackchen@qq.com", "Aa@12345"),
                Arrays.asList("jackchen001@qq.com", "Aa@12345"),
                Arrays.asList("jackchen002@qq.com", "Aa@12345"));
        for (List<String> account : accountPair) {
            UserLoginDTO loginDTO = new UserLoginDTO()
                    .withEmail(account.get(0))
                    .withPassword(account.get(1));
            ResponseDTO<?> responseDTO = loginUser_ValidateOK_Basic(loginDTO);

            UserLoginDTO data = buildDataTo(responseDTO, UserLoginDTO.class);
            assertNotNull(data);
            assertNotNull(data.getUserName());
            assertNotNull(data.getEmail());
            assertNotNull(data.getSessionId());

            // Login using sessionId
            HttpHeaders headers = new HttpHeaders();
            headers.add(UserContext.USER_SESSION_ID, data.getSessionId());
            responseDTO = loginUser_ValidateOK_Basic(null, headers);
            data = buildDataTo(responseDTO, UserLoginDTO.class);
            assertNotNull(data);
            assertNotNull(data.getUserName());
            assertNotNull(data.getEmail());
            assertNotNull(data.getSessionId());
        }
    }

    @Test
    @Order(80)
    public void logout_With_Session_Expire() throws Exception {
        initSampleUsers();

        List<List<String>> accountPair = Arrays.asList(
                Arrays.asList("jackchen@qq.com", "Aa@12345"),
                Arrays.asList("jackchen001@qq.com", "Aa@12345"),
                Arrays.asList("jackchen002@qq.com", "Aa@12345"));
        for (List<String> account : accountPair) {
            UserLoginDTO loginDTO = new UserLoginDTO()
                    .withEmail(account.get(0))
                    .withPassword(account.get(1));
            ResponseDTO<?> responseDTO = loginUser_ValidateOK_Basic(loginDTO);

            UserLoginDTO data = buildDataTo(responseDTO, UserLoginDTO.class);
            assertNotNull(data);
            assertNotNull(data.getUserName());
            assertNotNull(data.getEmail());
            assertNotNull(data.getSessionId());

            // logout
            HttpHeaders headers = new HttpHeaders();
            headers.add(UserContext.USER_SESSION_ID, data.getSessionId());
            logoutUser_ValidateOK_Basic(headers);

            System.out.println("session list: " + sessionRepository.findAll());

            // login using sessionId, rejected
            responseDTO = loginUser_ValidateFail_Basic(null, headers);
            Assert.assertEquals("Please enter account and password", responseDTO.getMessage());
        }
    }

    private ResponseDTO<?> loginUser_ValidateFail_Basic(UserLoginDTO loginDTO) throws Exception {
        return loginUser_ValidateFail_Basic(loginDTO, null);
    }

    private ResponseDTO<?> loginUser_ValidateFail_Basic(UserLoginDTO loginDTO, HttpHeaders headers) throws Exception {
        ResponseDTO<?> responseDTO = post("/user/login", loginDTO, headers);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        Assert.assertEquals((long)HttpStatus.BAD_REQUEST.value(), (long)responseDTO.getStatus());
        return responseDTO;
    }
}

