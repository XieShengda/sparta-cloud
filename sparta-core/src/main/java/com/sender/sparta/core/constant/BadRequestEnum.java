package com.sender.sparta.core.constant;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum BadRequestEnum {

    VALIDATION("参数校验失败，请检查请求参数是否有效"),
    ILLEGAL_ARGUMENT("Illegal Argument"),
    METHOD_ARGUMENT_TYPE_MISMATCH("Method Argument Type Mismatch"),
    MISSING_REQUEST_PARAMETER("Missing Servlet Request Parameter"),
    HTTP_MESSAGE_NOT_READABLE("Http Message Not Readable"),

    ;

    private String message;

    BadRequestEnum(String message) {
    }

}
