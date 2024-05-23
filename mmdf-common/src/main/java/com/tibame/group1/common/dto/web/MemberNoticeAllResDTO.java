package com.tibame.group1.common.dto.web;

import com.tibame.group1.common.dto.PagesResDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberNoticeAllResDTO {

    private PagesResDTO pages;

    private List<MemberNoticeResDTO> memberNoticeList;

    private Integer unReadCount;

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        QUERY_SUCCESS("1", "查詢成功"),

        MEMBER_NOTFOUND("-1", "查無此會員資料");

        private final String code;

        private final String message;
    }
}
