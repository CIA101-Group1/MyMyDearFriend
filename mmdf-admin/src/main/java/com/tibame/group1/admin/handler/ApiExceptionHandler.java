package com.tibame.group1.admin.handler;

import com.tibame.group1.common.exception.AuthorizationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends com.tibame.group1.common.handler.ApiExceptionHandler {
    @Override
    public String handleNoHandlerFoundException() throws AuthorizationException {
        throw new AuthorizationException("登入驗證碼不可為空");
    }
}
