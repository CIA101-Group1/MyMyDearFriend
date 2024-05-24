package com.tibame.group1.web.service;

import com.tibame.group1.common.dto.web.MemberNoticeAllResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeReqDTO;
import com.tibame.group1.common.dto.web.MemberNoticeUpdateResDTO;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.web.dto.LoginSourceDTO;

import org.springframework.data.domain.Pageable;

public interface MemberNoticeService {

    MemberNoticeAllResDTO memberNoticeAll(
            MemberNoticeReqDTO req, LoginSourceDTO loginSource, Pageable pageable)
            throws DateException;

    MemberNoticeUpdateResDTO memberNoticeUpdateReadingTime(LoginSourceDTO loginSource);
}
