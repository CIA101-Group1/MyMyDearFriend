package com.tibame.group1.common.exception;

/** 登入驗證碼錯誤 */
public class AuthorizationException extends Exception {

    public AuthorizationException(String message) {
        super(message);
    }
}
