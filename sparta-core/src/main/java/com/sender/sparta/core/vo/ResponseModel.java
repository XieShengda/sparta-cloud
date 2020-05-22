package com.sender.sparta.core.vo;

import lombok.Data;

@Data
public class ResponseModel {
    private final Integer code = 200;
    private final String message = "OK";
    private Object data;

    private ResponseModel() {
    }

    private ResponseModel(Object data) {
        this.data = data;
    }

    public static ResponseModel ok() {
        return new ResponseModel();
    }

    public static ResponseModel ok(Object data) {
        return new ResponseModel(data);
    }
}
