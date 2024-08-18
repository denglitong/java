package com.denglitong.mybatis_plus.quickstart.controller;

import com.denglitong.mybatis_plus.quickstart.dto.converter.UserConverter;
import com.denglitong.mybatis_plus.quickstart.dto.request.UserOperateRequest;
import com.denglitong.mybatis_plus.quickstart.entity.User;
import com.denglitong.mybatis_plus.quickstart.service.IUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 邓礼桐（deng.litong@foxmail.com）
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/quickstart/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private UserConverter userConverter;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<User> list() {
        return userService.list();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Boolean save(@Valid @RequestBody UserOperateRequest request) {
        return userService.save(userConverter.convert(request));
    }
}

