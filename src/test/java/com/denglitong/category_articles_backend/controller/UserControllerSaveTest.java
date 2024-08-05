package com.denglitong.category_articles_backend.controller;

import com.denglitong.category_articles_backend.Application;
import com.denglitong.category_articles_backend.DTO.ResponseDTO;
import com.denglitong.category_articles_backend.DTO.UserDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:/category_articles_backend/application.yml")
public class UserControllerSaveTest extends BaseControllerTest {

    // TODO hibernate validator order between @NotBlank vs @Size ?
    @Test
    public void saveUser_UserName_Blank() throws Exception {
        String[] userNameBlank = new String[]{null, "", "   "};
        for (String userName : userNameBlank) {
            UserDTO userDTO = new UserDTO().withUserName(userName);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: userName: userName can not be blank",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_UserName_Length() throws Exception {
        String[] userNameBlank = new String[]{"a", "ab", "abc", "abcd"};
        for (String userName : userNameBlank) {
            UserDTO userDTO = new UserDTO().withUserName(userName);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: userName: userName length must within [5, 20]",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_UserName_Pattern() throws Exception {
        String[] userNameBlank = new String[]{"abcd~", "12@34", "⛔️ab12", "cd3·4"};
        for (String userName : userNameBlank) {
            UserDTO userDTO = new UserDTO().withUserName(userName);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals(
                    "Validation fail: userName: userName can only contain digit [0-9] or letter [A-Za-z]",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_UserName_Unique() throws Exception {
        initSampleUsers();
        String[] userNameExist = new String[]{"jackchen", "jackchen001", "jackchen002"};
        for (String userName : userNameExist) {
            UserDTO userDTO = new UserDTO().withUserName(userName);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: userName: userName already exist",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_Email_Blank() throws Exception {
        String randomUserName = "rand" + (System.currentTimeMillis() % 10000000);
        String[] emailBlank = new String[]{null, "", "   "};
        for (String email : emailBlank) {
            UserDTO userDTO = new UserDTO().withUserName(randomUserName).withEmail(email);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: email: email can not be blank",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_Email_Invalid() throws Exception {
        String randomUserName = "rand" + (System.currentTimeMillis() % 10000000);
        String[] emailBlank = new String[]{"aaa", "aaa @ ",
                "abc..def@mail.com", ".abc@mail.com", "abc.def@mail..com"};
        for (String email : emailBlank) {
            UserDTO userDTO = new UserDTO().withUserName(randomUserName).withEmail(email);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: email: email must have a valid value",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_Email_Unique() throws Exception {
        initSampleUsers();

        String randomUserName = "rand" + (System.currentTimeMillis() % 10000000);
        String[] emailExist = new String[]{"jackchen@qq.com", "jackchen001@qq.com", "jackchen002@qq.com"};
        for (String email : emailExist) {
            UserDTO userDTO = new UserDTO().withUserName(randomUserName).withEmail(email);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: email: email already exist",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_Password_Blank() throws Exception {
        String randomUserName = "rand" + (System.currentTimeMillis() % 10000000);
        String randomEmail = randomUserName + "@qq.com";
        String[] passwordBlank = new String[]{null, "", "   "};
        for (String password : passwordBlank) {
            UserDTO userDTO = new UserDTO()
                    .withUserName(randomUserName)
                    .withEmail(randomEmail)
                    .withPassword(password);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: password: password can not be blank",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_Password_Size() throws Exception {
        String randomUserName = "rand" + (System.currentTimeMillis() % 10000000);
        String randomEmail = randomUserName + "@qq.com";
        String[] passwordOutSize = new String[]{"Aa@12", "Aa@12abcdefghijklmnopqrstuvwxyz"};
        for (String password : passwordOutSize) {
            UserDTO userDTO = new UserDTO()
                    .withUserName(randomUserName)
                    .withEmail(randomEmail)
                    .withPassword(password);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: password: password length must within [8, 20]",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_Password_Pattern_Uppercase() throws Exception {
        String randomUserName = "rand" + (System.currentTimeMillis() % 10000000);
        String randomEmail = randomUserName + "@qq.com";
        String[] passwordOutSize = new String[]{"a@12abcdefghijkl", "a@12345678"};
        for (String password : passwordOutSize) {
            UserDTO userDTO = new UserDTO()
                    .withUserName(randomUserName)
                    .withEmail(randomEmail)
                    .withPassword(password);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: password: password must contain at least one uppercase letter",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_Password_Pattern_Lowercase() throws Exception {
        String randomUserName = "rand" + (System.currentTimeMillis() % 10000000);
        String randomEmail = randomUserName + "@qq.com";
        String[] passwordOutSize = new String[]{"A@12ABCDEFGHIJKL", "Z@12345678"};
        for (String password : passwordOutSize) {
            UserDTO userDTO = new UserDTO()
                    .withUserName(randomUserName)
                    .withEmail(randomEmail)
                    .withPassword(password);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: password: password must contain at least one lowercase letter",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_Password_Pattern_Digit() throws Exception {
        String randomUserName = "rand" + (System.currentTimeMillis() % 10000000);
        String randomEmail = randomUserName + "@qq.com";
        String[] passwordOutSize = new String[]{"Aabc@ABCDEFG", "Z@asfsdsfsdsfs"};
        for (String password : passwordOutSize) {
            UserDTO userDTO = new UserDTO()
                    .withUserName(randomUserName)
                    .withEmail(randomEmail)
                    .withPassword(password);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: password: password must contain at least one digit number",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_Password_Pattern_Non_Alphanumeric() throws Exception {
        String randomUserName = "rand" + (System.currentTimeMillis() % 10000000);
        String randomEmail = randomUserName + "@qq.com";
        String[] passwordOutSize = new String[]{"abc123ABCDEFG", "Z456Jack"};
        for (String password : passwordOutSize) {
            UserDTO userDTO = new UserDTO()
                    .withUserName(randomUserName)
                    .withEmail(randomEmail)
                    .withPassword(password);
            ResponseDTO<?> responseDTO = saveUser_ValidateFail_Basic(userDTO);
            Assert.assertEquals("Validation fail: password: password must contain at least one non-alphanumeric character",
                    responseDTO.getMessage());
        }
    }

    @Test
    public void saveUser_Valid() throws Exception {
        for (int i = 0; i < 4; i++) {
            String userName = "" + (System.currentTimeMillis() % 10000000);
            UserDTO userDTO = new UserDTO()
                    .withUserName(userName)
                    .withEmail(userName + "@qq.com")
                    .withPassword("Aa@12345");
            ResponseDTO<?> responseDTO = saveUser_ValidateOK_Basic(userDTO);

            UserDTO data = buildDataTo(responseDTO, UserDTO.class);
            assertNotNull(data);
            assertNotNull(data.getUserId());
            Assert.assertEquals(userName, data.getUserName());
        }
    }

    private ResponseDTO<?> saveUser_ValidateFail_Basic(UserDTO userDTO) throws Exception {
        ResponseDTO<?> responseDTO = post("/user", userDTO);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        Assert.assertEquals((long)HttpStatus.BAD_REQUEST.value(), (long)responseDTO.getStatus());
        return responseDTO;
    }

    private ResponseDTO<?> saveUser_ValidateOK_Basic(UserDTO userDTO) throws Exception {
        ResponseDTO<?> responseDTO = post("/user", userDTO);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getStatus());
        Assert.assertEquals((long)HttpStatus.OK.value(), (long)responseDTO.getStatus());
        return responseDTO;
    }
}
