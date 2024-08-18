package com.denglitong.mybatis_plus.quickstart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.denglitong.mybatis_plus.quickstart.entity.User;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 邓礼桐（deng.litong@foxmail.com）
 * @since 2021-04-02
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 根据年龄查询用户列表
	 *
	 * @param age
	 * @return
	 */
	List<User> listByAge(Integer age);
}
