package com.denglitong.category_articles_backend.DTO;

import com.denglitong.category_articles_backend.validate.AttrsNotAllBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
@AttrsNotAllBlank(attributes = {"userName", "email"}, message = "userName and email can not be all blank")
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
public class UserLoginDTO {

    private String userName;

    private String email;

    @NotBlank(message = "password can not be blank")
    private String password;

    private boolean rememberMe;

    private String sessionId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserLoginDTO withUserName(String userName) {
        setUserName(userName);
        return this;
    }

    public UserLoginDTO withEmail(String email) {
        setEmail(email);
        return this;
    }

    public UserLoginDTO withPassword(String password) {
        setPassword(password);
        return this;
    }

    public UserLoginDTO withRememberMe(boolean rememberMe) {
        setRememberMe(rememberMe);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
