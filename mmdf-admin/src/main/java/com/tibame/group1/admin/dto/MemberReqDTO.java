package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberReqDTO {

    private Boolean isVerified;

    private Boolean sellerStatus;

    private String searchText;
}
