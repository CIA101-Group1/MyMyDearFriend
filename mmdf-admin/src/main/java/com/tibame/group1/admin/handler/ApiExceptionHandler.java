package com.tibame.group1.admin.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public abstract class ApiExceptionHandler
        extends com.tibame.group1.common.handler.ApiExceptionHandler {}
