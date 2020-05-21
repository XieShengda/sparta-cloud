package com.sender.sparta.constant;

public class TokenKey {
    /**
     * Bearer:token -> hash
     */
    public static final String TOKEN_PREFIX = "Bearer:";
    /**
     * tokens:username -> set
     */
    public static final String TOKEN_SET_PREFIX = "tokens:";
    /**
     * users:username -> UserDetails.class
     */
    public static final String USER_PREFIX = "users:";
    /**
     * key:token
     * value:username
     */
    public static final String TOKEN_USER_MAPPING = "token_user_mapping";
}
