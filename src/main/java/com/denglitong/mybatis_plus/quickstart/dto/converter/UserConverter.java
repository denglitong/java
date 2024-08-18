package com.denglitong.mybatis_plus.quickstart.dto.converter;

import com.denglitong.mybatis_plus.quickstart.dto.request.UserOperateRequest;
import com.denglitong.mybatis_plus.quickstart.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author denglitong
 * @date 2021/4/4
 */
@Component
public class UserConverter {

    public User convert(UserOperateRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        return user;
    }
}
