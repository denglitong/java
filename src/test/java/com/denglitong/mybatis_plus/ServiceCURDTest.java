package com.denglitong.mybatis_plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.denglitong.mybatis_plus.common.util.JSONUtil;
import com.denglitong.mybatis_plus.quickstart.entity.User;
import com.denglitong.mybatis_plus.quickstart.mapper.UserMapper;
import com.denglitong.mybatis_plus.quickstart.service.IUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisPlusApplication.class)
@ActiveProfiles("mybatis_plus")
class ServiceCURDTest {

	@Resource
	private UserMapper userMapper;

	@Resource
	private IUserService userService;

	@Test
	void testSave() {
		User user = new User();
		user.setName("name1");
		user.setAge(18);
		user.setEmail("name1@example.com");
		Assertions.assertTrue(userService.save(user));
	}

	@Test
	void testBatchUpdate() {
		List<User> userList = userService.list();
		userList.forEach(user -> user.setName(user.getName() + "_1"));
		Assertions.assertTrue(userService.saveOrUpdateBatch(userList));
	}

	@Test
	void testRemove() {
		QueryWrapper<User> query = Wrappers.query();
		query.orderByDesc("id");
		query.last("limit 1");
		User user = userService.getOne(query);
		Assertions.assertTrue(userService.removeById(user.getId()));
	}

	@Test
	void testGet() {
		QueryWrapper<User> query = Wrappers.query();
		query.orderByDesc("id");
		query.last("limit 1");
		Map<String, Object> resultMap = userService.getMap(query);
		System.out.println(resultMap);
	}

	@Test
	void testListByMap() {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("age", 24);
		List<User> userList = userService.listByMap(columnMap);
		userList.forEach(System.out::println);
	}

	@Test
	void testPage() throws JsonProcessingException {
		IPage<User> page = new Page<>(1, 2);
		IPage<User> userList = userService.page(page);
		System.out.println(JSONUtil.generate(userList));

		page.setCurrent(page.getCurrent() + 1);
		userList = userService.page(page);
		System.out.println(JSONUtil.generate(userList));
	}

	@Test
	void testCount() {
		Assertions.assertTrue(userService.count() > 0);
		QueryWrapper<User> queryWrapper = Wrappers.query();
		queryWrapper.eq("age", 24);
		Assertions.assertTrue(userService.count(queryWrapper) > 0);
	}

	@Test
	void testChain() {
		QueryChainWrapper<User> queryChainWrapper = ChainWrappers.queryChain(userMapper);
		// where age = 24
		List<User> userList = queryChainWrapper.eq("age", 24).list();
		System.out.println(userList);
		// where age = 24 and id = 5
		User user = queryChainWrapper.eq("id", 5).one();
		System.out.println(user);

		LambdaQueryChainWrapper<User> lambdaQueryChainWrapper = ChainWrappers.lambdaQueryChain(userMapper);
		userList = lambdaQueryChainWrapper.eq(User::getAge, 24).list();
		System.out.println(userList);
		user = lambdaQueryChainWrapper.eq(User::getId, 5).one();
		System.out.println(user);

		UpdateChainWrapper<User> updateChainWrapper = ChainWrappers.updateChain(userMapper);
		updateChainWrapper.eq("age", 24).set("name", "**").update();

		LambdaUpdateChainWrapper<User> lambdaUpdateChainWrapper = ChainWrappers.lambdaUpdateChain(userMapper);
		lambdaUpdateChainWrapper.eq(User::getAge, 24).set(User::getName, "***").update();
	}
}