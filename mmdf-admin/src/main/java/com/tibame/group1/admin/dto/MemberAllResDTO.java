package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAllResDTO {

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
