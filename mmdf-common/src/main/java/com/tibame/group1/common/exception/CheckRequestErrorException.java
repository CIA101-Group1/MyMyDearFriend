package com.tibame.group1.common.exception;

/** 檢查傳入資料不符合 */
public class CheckRequestErrorException extends Exception {

    public CheckRequestErrorException(String message) {
        super(message);
    }
}
