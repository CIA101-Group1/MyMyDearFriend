package com.tibame.group1.common.dto.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDetailResDTO {
    private Integer memberId;

    private String memberAccount;

    private String cid;

    private String name;

    private String phone;

    private String email;

    private String birth;

    private String twPersonId;

    private String city;

    private String address;

    private Boolean isVerified;

    private String verificationSendingTime;

    private String verifiedTime;

    private String joinTime;

    private Integer walletAmount;

    private Integer walletAvailableAmount;

    private String walletCid;

    private String walletQuestion;

    private String walletAnswer;

    private Boolean sellerStatus;

    private Integer scoreNumber;

    private Integer scoreSum;

    private String imageBase64;
}
