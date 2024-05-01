package com.tibame.group1.common.exception;

/** 資料已存在錯誤 */
public class AlreadyExistException extends Exception {

    public AlreadyExistException(String message) {
        super(message);
    }
}
