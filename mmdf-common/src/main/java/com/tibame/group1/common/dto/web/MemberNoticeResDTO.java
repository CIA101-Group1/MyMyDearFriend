package com.tibame.group1.common.dto.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberNoticeResDTO {
    private String memberNoticeId;

    private String memberId;

    private String noticeTitle;

    private String noticeContent;

    private String noticeCategory;

    private String sendingTime;

    private String lastReadingTime;
}
