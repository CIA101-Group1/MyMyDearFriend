package com.tibame.group1.common.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDetailResDTO {
    private String memberAccount;

    private String name;

    private String phone;

    private String email;

    private String birth;

    private String twPersonId;

    private String city;

    private String address;

    private Boolean isVerified;

    private Boolean sellerStatus;

    private Integer scoreNumber;

    private Integer scoreSum;

    private String imageBase64;

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
