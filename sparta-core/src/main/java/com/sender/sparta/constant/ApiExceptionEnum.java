package com.sender.sparta.constant;


import lombok.Getter;

@Getter
public enum ApiExceptionEnum {
    NOT_EXIST(1001, "请求的内容不存在或已删除");

    private Integer code;
    private String message;

    ApiExceptionEnum(Integer code, String message) {
    }

}
