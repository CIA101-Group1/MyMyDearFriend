package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResDTO {

    private String memberId;

    private String memberAccount;

    private String name;

    private String phone;

    private String email;

    private String birth;

    private String city;

    private String address;

    private Boolean isVerified;

    private String verifySendingTime;

    private String verifiedTime;

    private String joinTime;

    private Integer walletAmount;

    private Integer walletAvailableAmount;

    private Boolean sellerStatus;

    private Integer scoreNumber;

    private Integer scoreSum;

    private String imageBase64;

    private String cidResetSendingTime;
}
