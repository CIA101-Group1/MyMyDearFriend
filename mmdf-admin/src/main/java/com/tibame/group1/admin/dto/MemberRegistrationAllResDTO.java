package com.tibame.group1.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegistrationAllResDTO {
    private Integer memberId;

    private String name;

    private String phone;

    private String email;

    private String city;

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        PAY_SUCCESS("1", "已繳費"),

        PAY_ERROR("-1", "尚未繳費");

        private final String code;

        private final String message;
    }
}
