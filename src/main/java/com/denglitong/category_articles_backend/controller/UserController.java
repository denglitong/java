package com.denglitong.category_articles_backend.controller;

import com.denglitong.category_articles_backend.DTO.UserDTO;
import com.denglitong.category_articles_backend.DTO.UserLoginDTO;
import com.denglitong.category_articles_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/27
 */
@RestController
@RequestMapping("/rest/user")
@Validated
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册用户
     *
     * @param userDTO
     * @return
     */
    @PostMapping
    public UserDTO save(@RequestBody @Validated(UserDTO.SAVE.class) UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @GetMapping("/{userName}")
    public UserDTO getUserByName(@PathVariable("userName") String userName) {
        return userService.findByUserName(userName);
    }

    @GetMapping("/list")
    public List<UserDTO> list() {
        return userService.findAll();
    }

    /**
     * 用户登录
     * <p>
     * frontend set sessionId in chrome local storage,
     * when frontend setup, first check if sessionId exist then query login api to get user session
     *
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public UserLoginDTO login(@RequestBody(required = false) @Validated UserLoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @PostMapping("/logout")
    public void logout() {
        userService.logout();
    }
}
