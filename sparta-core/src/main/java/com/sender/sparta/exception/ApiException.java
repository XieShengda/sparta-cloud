package com.sender.sparta.exception;

import com.sender.sparta.constant.ApiExceptionEnum;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private static final Integer DEFAULT_API_EXCEPTION_CODE = 1000;
    private final Integer code;

    private ApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 抛出通用api异常
     * 通常前端只需要提示，不需要特定的操作
     *
     * @param message 异常信息
     */
    public static void abort(String message) throws ApiException {
        throw new ApiException(DEFAULT_API_EXCEPTION_CODE, message);
    }

    /**
     * 抛出预设api异常
     * 前端需要根据code执行特定的操作
     */
    public static void abort(ApiExceptionEnum e) throws ApiException {
        throw new ApiException(e.getCode(), e.getMessage());
    }

}
