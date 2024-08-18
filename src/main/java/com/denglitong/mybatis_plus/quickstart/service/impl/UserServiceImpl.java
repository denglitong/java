package com.denglitong.mybatis_plus.quickstart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.denglitong.mybatis_plus.quickstart.entity.User;
import com.denglitong.mybatis_plus.quickstart.mapper.UserMapper;
import com.denglitong.mybatis_plus.quickstart.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 邓礼桐（deng.litong@foxmail.com）
 * @since 2021-04-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
