package com.denglitong.mybatis_plus;

import com.denglitong.mybatis_plus.quickstart.entity.Employee;
import com.denglitong.mybatis_plus.quickstart.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 邓礼桐（deng.litong@foxmail.com）
 * @date 2021/4/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisPlusApplication.class)
@ActiveProfiles("mybatis_plus")
class SequenceTest {

    @Resource
    private EmployeeMapper employeeMapper;

    @Test
    void testInsert() {
        for (int i = 0; i < 3; i++) {
            Employee employee = new Employee();
            employee.setName("name" + i);
            employee.setAge(18 + i);
            employee.setEmail("name" + i + "@foxmail.com");
            employeeMapper.insert(employee);
        }
    }
}
