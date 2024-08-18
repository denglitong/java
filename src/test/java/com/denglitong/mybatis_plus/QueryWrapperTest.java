package com.denglitong.mybatis_plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.denglitong.mybatis_plus.quickstart.entity.User;
import com.denglitong.mybatis_plus.quickstart.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 邓礼桐（deng.litong@foxmail.com）
 * @date 2021/4/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisPlusApplication.class)
@ActiveProfiles("mybatis_plus")
class QueryWrapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void testAllEq() {
        QueryWrapper<User> queryWrapper = Wrappers.query();
        Map<String, Object> params = new HashMap<>();
        params.put("age", 24);
        queryWrapper.allEq(params);
        // where age = 24
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);

        QueryWrapper<User> queryWrapper2 = Wrappers.query();
        params.put("name", null);
        queryWrapper2.allEq(params);
        // where age = 24 and name is null
        userList = userMapper.selectList(queryWrapper2);
        userList.forEach(System.out::println);

        QueryWrapper<User> queryWrapper3 = Wrappers.query();
        // where age = 24
        queryWrapper3.allEq(params, false); // filter condition which column value is null when false
        userList = userMapper.selectList(queryWrapper3);
        userList.forEach(System.out::println);

        QueryWrapper<User> queryWrapper4 = Wrappers.query();
        queryWrapper4.allEq(params, true);
        // where age = 24 and name is null
        userList = userMapper.selectList(queryWrapper4);
        userList.forEach(System.out::println);
    }

    @Test
    void testAbstractWrapper() {
        QueryWrapper<User> queryWrapper = Wrappers.query();
        queryWrapper.eq("age", 24);

		/*
		queryWrapper.ne("name", null);
		queryWrapper.gt("id", 1);
		queryWrapper.lt("id", 10);
		queryWrapper.ge("id", 2);
		queryWrapper.le("age", 30);
		queryWrapper.between("age", 1, 30);
		queryWrapper.notBetween("id", 10, 20);
		queryWrapper.like("name", "王"); // %王%
		queryWrapper.notLike("name", null);
		queryWrapper.likeLeft("name", "王"); // %王
		queryWrapper.likeRight("name", "王"); // 王%
		queryWrapper.isNull("name"); // name is null
		queryWrapper.isNotNull("email"); // email is not null
		queryWrapper.in("id", (Object) new Integer[]{2, 3, 4, 5, 6});
		queryWrapper.notIn("id", 0, 1);
		// where id in (1,2,3,4,5,6)
		queryWrapper.inSql("id", "1,2,3,4,5,6");
		// where id in (select id from table where id < 3)
		queryWrapper.inSql("id", "select id from table where id < 3");
		// where age not in (1,2,3,4,5,6)
		queryWrapper.notInSql("age", "1,2,3,4,5,6");
		// where age not in (select age from table where age < 3)
		queryWrapper.notInSql("age", "select age from table where id < 3");
		// group by name, age
		queryWrapper.groupBy("name", "age");
		// order by age desc, name desc
		queryWrapper.orderByDesc("age", "name");
		// order by name asc
		queryWrapper.orderBy(true, true, "name");
		// order by name asc
		queryWrapper.orderByAsc("name");
		// having sum(age) > 100
		queryWrapper.having("sum(age) > 100");
		// func 主要方便在出现if...else时调用不同方法并不断链
		queryWrapper.func(i -> {
			if (true) {
				i.eq("id", 1);
			} else {
				i.ne("id", 1);
			}
		});
		// id = 1 and name = '老王'
		queryWrapper.eq("id", 1).or().eq("name", "老王");
		// or (name = '李白' and status = 1)
		queryWrapper.or(i -> i.eq("name", "李白").ne("status", 1));
		// 正常嵌套 不带 AND 或者 OR
		// (name = '李白' and status = 1)
		queryWrapper.nested(i -> i.eq("name", "李白").ne("status", 1));
		// 拼接sql，使用 {index} 方式可避免注入风险
		queryWrapper.apply("date_format(date, '%Y-%m-%d') = {0}", "2018-08-08");
		queryWrapper.last("limit 1");
		// exists (select id from table where age = 24)
		queryWrapper.exists("select id from table where age = 24");
		// not exists (select id from table where age = 24)
		queryWrapper.notExists("select id from table where age = 24");
		*/

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    void testQueryWrapper() {
        QueryWrapper<User> queryWrapper = Wrappers.query();
        // select id, name, age
        queryWrapper.select("id", "name", "age");
        // queryWrapper.select(i -> i.getProperty().startsWith("n"));

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    void testUpdateWrapper() {
        UpdateWrapper<User> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", 1);
        updateWrapper.set("name", "yinli");
        userMapper.update(null, updateWrapper);
    }
}
