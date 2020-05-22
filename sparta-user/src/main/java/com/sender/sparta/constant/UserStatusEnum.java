package com.sender.sparta.constant;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public enum UserStatusEnum {
    ENABLE(0),
    FROZEN(1),
    DISABLE(2),
    ;
    Integer code;

    UserStatusEnum(Integer code) {
        this.code = code;
    }
}
