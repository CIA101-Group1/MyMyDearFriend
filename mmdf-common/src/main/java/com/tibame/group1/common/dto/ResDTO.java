package com.tibame.group1.common.dto;

import com.tibame.group1.common.enums.StatusCode;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ResDTO<T> {

    private String code;

    @Setter private String message;

    @Setter private T data;

    public ResDTO() {
        this.setStatusCode(StatusCode.SUCCESS);
    }

    public ResDTO(StatusCode statusCode) {
        this.setStatusCode(statusCode);
    }

    public void setStatusCode(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }
}
