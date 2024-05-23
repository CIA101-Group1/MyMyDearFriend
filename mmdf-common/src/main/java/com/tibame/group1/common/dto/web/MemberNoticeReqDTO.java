package com.tibame.group1.common.dto.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberNoticeReqDTO {

    private String noticeCategory;

    private String sendingTimeBegin;

    private String sendingTimeEnd;
}
