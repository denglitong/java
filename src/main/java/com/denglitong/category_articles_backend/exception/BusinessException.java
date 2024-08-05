package com.denglitong.category_articles_backend.exception;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
public class BusinessException extends RuntimeException {

    private int code;

    private String message;

    public BusinessException() {}

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
