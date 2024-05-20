package com.tibame.group1.admin.dto;

import com.tibame.group1.common.dto.PagesResDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberResDTO {

    private PagesResDTO pages;

    private List<MemberAllResDTO> memberList;

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        SEARCH_SUCCESS("1", "查詢成功"),

        EMPLOYEE_NOTFOUND("-1", "查無此員工");

        private final String code;

        private final String message;
    }
}
