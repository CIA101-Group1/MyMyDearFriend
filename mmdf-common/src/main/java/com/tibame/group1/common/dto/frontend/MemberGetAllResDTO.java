package com.tibame.group1.common.dto.frontend;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberGetAllResDTO {
    private List<MemberResDTO> memberList;
}
