package com.denglitong.mybatis_plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.denglitong.mybatis_plus.quickstart.entity.User;
import com.denglitong.mybatis_plus.quickstart.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 邓礼桐（deng.litong@foxmail.com）
 * @date 2021/4/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisPlusApplication.class)
@ActiveProfiles("mybatis_plus")
class MapperCURDTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
        user.setName("name2");
        user.setAge(19);
        user.setEmail("name2@example.com");
        Assertions.assertTrue(userMapper.insert(user) > 0);
    }

    @Test
    void testDelete() {
        QueryWrapper<User> queryWrapper = Wrappers.query();
        queryWrapper.eq("name", "name2");
        Assertions.assertTrue(userMapper.delete(queryWrapper) > 0);
    }

    @Test
    void testUpdate() {
        UpdateWrapper<User> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", 1);
        User user = new User();
        user.setName("yin1");
        Assertions.assertTrue(userMapper.update(user, updateWrapper) > 0);

        user.setId(1L);
        user.setEmail(null);
        Assertions.assertTrue(userMapper.updateById(user) > 0);
    }

    @Test
    void testSelect() {
        List<User> userList = userMapper.selectList(null);
        Assertions.assertFalse(userList.isEmpty());
        userList.forEach(System.out::println);
    }
}
