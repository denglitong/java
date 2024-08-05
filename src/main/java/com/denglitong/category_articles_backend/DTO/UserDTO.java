package com.denglitong.category_articles_backend.DTO;

import com.denglitong.category_articles_backend.entity.UserEntity;
import com.denglitong.category_articles_backend.validate.UniqueValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
public class UserDTO {

    private Integer userId;

    @NotBlank(message = "userName can not be blank",
            groups = {SAVE.USER_NAME.class, SAVE.NOT_BLANK.class})
    @Size(min = 5, max = 20, message = "userName length must within [5, 20]",
            groups = {SAVE.USER_NAME.class, SAVE.SIZE.class})
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "userName can only contain digit [0-9] or letter [A-Za-z]",
            groups = {SAVE.USER_NAME.class, SAVE.PATTERN.class,})
    @UniqueValue(entityClass = UserEntity.class, column = UserEntity.USER_NAME, message = "userName already exist",
            groups = {SAVE.USER_NAME.class, SAVE.UNIQUE_VALUE.class,})
    private String userName;

    @NotBlank(message = "email can not be blank",
            groups = {SAVE.USER_EMAIL.class, SAVE.NOT_BLANK.class})
    @Email(message = "email must have a valid value",
            groups = {SAVE.USER_EMAIL.class, SAVE.EMAIL.class,})
    @UniqueValue(entityClass = UserEntity.class, column = UserEntity.EMAIL, message = "email already exist",
            groups = {SAVE.USER_EMAIL.class, SAVE.UNIQUE_VALUE.class,})
    private String email;

    @NotBlank(message = "password can not be blank",
            groups = {SAVE.PASSWORD.class, SAVE.NOT_BLANK.class,})
    @Size(min = 8, max = 20, message = "password length must within [8, 20]",
            groups = {SAVE.PASSWORD.class, SAVE.SIZE.class})
    @Pattern(regexp = ".*[A-Z]+.*", message = "password must contain at least one uppercase letter",
            groups = {SAVE.PASSWORD.class, SAVE.PATTERN.class})
    @Pattern(regexp = ".*[a-z]+.*", message = "password must contain at least one lowercase letter",
            groups = {SAVE.PASSWORD.class, SAVE.PATTERN.class})
    @Pattern(regexp = ".*[0-9]+.*", message = "password must contain at least one digit number",
            groups = {SAVE.PASSWORD.class, SAVE.PATTERN.class})
    @Pattern(regexp = ".*[^A-Za-z0-9]+.*", message = "password must contain at least one non-alphanumeric character",
            groups = {SAVE.PASSWORD.class, SAVE.PATTERN.class})
    private String password;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public UserDTO withUserId(Integer userId) {
        setUserId(userId);
        return this;
    }

    public UserDTO withUserName(String userName) {
        setUserName(userName);
        return this;
    }

    public UserDTO withEmail(String email) {
        setEmail(email);
        return this;
    }

    public UserDTO withPassword(String password) {
        setPassword(password);
        return this;
    }

    public UserDTO withCreatedTime(LocalDateTime createTime) {
        setCreatedTime(createTime);
        return this;
    }

    public UserDTO withUpdatedTime(LocalDateTime updatedTime) {
        setUpdatedTime(updatedTime);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @GroupSequence({
            SAVE.USER_NAME.class,
            SAVE.USER_EMAIL.class,
            SAVE.PASSWORD.class,
            SAVE.NOT_NULL.class,
            SAVE.NOT_BLANK.class,
            SAVE.SIZE.class,
            SAVE.PATTERN.class,
            SAVE.EMAIL.class,
            SAVE.UNIQUE_VALUE.class,
    })
    public interface SAVE {
        interface USER_NAME {}
        interface USER_EMAIL {}
        interface PASSWORD {}
        interface NOT_NULL {}
        interface NOT_BLANK {}
        interface SIZE {}
        interface PATTERN {}
        interface EMAIL {}
        interface UNIQUE_VALUE {}
    }
}
