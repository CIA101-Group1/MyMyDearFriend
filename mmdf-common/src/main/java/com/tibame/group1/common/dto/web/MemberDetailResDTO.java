package com.tibame.group1.common.dto.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDetailResDTO {
    private String memberId;

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

    private String scoreNumber;

    private String scoreSum;

    private String imageBase64;
}
