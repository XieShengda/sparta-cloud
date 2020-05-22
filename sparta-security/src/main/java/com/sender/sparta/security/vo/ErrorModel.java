package com.sender.sparta.security.vo;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorModel {
    private Integer code;
    private String message;

    private ErrorModel(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorModel unauthorized(String message) {
        return new ErrorModel(HttpStatus.UNAUTHORIZED.value(), message);
    }

    public static ErrorModel forbidden(String message) {
        return new ErrorModel(HttpStatus.FORBIDDEN.value(), message);
    }
}
