package com.denglitong.category_articles_backend.exception;

import com.denglitong.category_articles_backend.DTO.ResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("Validation fail: ");
        for (ObjectError objectError : bindingResult.getGlobalErrors()) {
            sb.append(objectError.getObjectName()).append(": ").append(objectError.getDefaultMessage()).append(", ");
        }
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = StringUtils.removeEnd(sb.toString(), ", ");
        return ResponseDTO.fail(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ResponseDTO handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseDTO.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public ResponseDTO handleRuntimeException(RuntimeException ex) {
        if (ex instanceof BusinessException) {
            return ResponseDTO.fail(((BusinessException)ex).getCode(), ex.getMessage());
        }
        return ResponseDTO.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
