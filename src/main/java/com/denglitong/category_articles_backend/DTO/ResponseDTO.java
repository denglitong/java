package com.denglitong.category_articles_backend.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    private Integer status;
    private String message;
    private T data;

    public static <T> ResponseDTO fail(Integer status, String msg) {
        return new ResponseDTO()
                .withStatus(status)
                .withMessage(msg);
    }

    public static <T> ResponseDTO success(T data) {
        return new ResponseDTO()
                .withStatus(HttpStatus.OK.value())
                .withData(data);
    }

    public ResponseDTO<T> withStatus(Integer status) {
        setStatus(status);
        return this;
    }

    public ResponseDTO<T> withMessage(String msg) {
        setMessage(msg);
        return this;
    }

    public ResponseDTO<T> withData(T data) {
        setData(data);
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
