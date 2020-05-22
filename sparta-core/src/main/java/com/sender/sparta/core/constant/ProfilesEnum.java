package com.sender.sparta.core.constant;

public enum ProfilesEnum {
    DEVELOPMENT("dev"),
    TEST("test"),
    PRODUCTION("prod"),
    ;
    private final String code;

    ProfilesEnum(String code) {
        this.code = code;
    }

    public static Boolean inProduction(String active) {
        return PRODUCTION.code.equals(active);
    }
}
