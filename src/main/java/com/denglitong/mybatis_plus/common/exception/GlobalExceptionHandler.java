package com.denglitong.mybatis_plus.common.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @author denglitong
 * @date 2021/4/4
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BindException.class, ConstraintViolationException.class})
    @ResponseBody
    public String validatorExceptionHandler(Exception e) {
        String msg = e instanceof BindException ? msgConvertor(((BindException)e).getBindingResult())
                : msgConvertor(((ConstraintViolationException)e).getConstraintViolations());
        return msg;
    }

    /**
     * 校验消息转换拼接
     *
     * @param bindingResult
     * @return
     */
    private static String msgConvertor(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        fieldErrors.forEach(fieldError -> sb.append(fieldError.getDefaultMessage()).append(","));
        return sb.deleteCharAt(sb.length() - 1).toString().toLowerCase();
    }

    private String msgConvertor(Set<ConstraintViolation<?>> constraintViolations) {
        StringBuilder sb = new StringBuilder();
        constraintViolations.forEach(violation -> sb.append(violation.getMessage()).append(","));
        return sb.deleteCharAt(sb.length() - 1).toString().toLowerCase();
    }
}
