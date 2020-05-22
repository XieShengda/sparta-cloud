package com.sender.sparta.core.constant;


import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum ApiExceptionEnum {
    NOT_EXIST(1001, "请求的内容不存在或已删除");

    private Integer code;
    private String message;

    ApiExceptionEnum(Integer code, String message) {
    }

}
