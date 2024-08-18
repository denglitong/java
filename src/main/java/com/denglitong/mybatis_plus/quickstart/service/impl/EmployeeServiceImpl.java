package com.denglitong.mybatis_plus.quickstart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.denglitong.mybatis_plus.quickstart.entity.Employee;
import com.denglitong.mybatis_plus.quickstart.mapper.EmployeeMapper;
import com.denglitong.mybatis_plus.quickstart.service.IEmployeeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 邓礼桐（deng.litong@foxmail.com）
 * @since 2021-04-03
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
