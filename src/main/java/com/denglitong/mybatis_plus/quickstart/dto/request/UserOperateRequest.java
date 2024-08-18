package com.denglitong.mybatis_plus.quickstart.dto.request;

import com.denglitong.mybatis_plus.quickstart.entity.enums.GradeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author denglitong
 * @date 2021/4/4
 */
public class UserOperateRequest implements Serializable {

    private static final long serialVersionUID = 5730186347145579192L;

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotNull(message = "年龄不能为空")
    private Integer age;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotNull(message = "年级不能为空")
    private GradeEnum grade;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public GradeEnum getGrade() {
        return grade;
    }
    public void setGrade(GradeEnum grade) {
        this.grade = grade;
    }
}
