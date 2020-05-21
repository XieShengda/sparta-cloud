package com.sender.sparta.exception;

/**
 * 请求发生冲突，前端应该禁止重复请求
 */
public class ConflictException extends RuntimeException {

    private ConflictException() {
    }

    public static void abort() throws ConflictException {
        throw new ConflictException();
    }

}
