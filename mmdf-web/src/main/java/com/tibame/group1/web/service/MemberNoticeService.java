package com.tibame.group1.web.service;

import com.tibame.group1.common.dto.web.MemberNoticeAllResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeReqDTO;
import com.tibame.group1.common.exception.DateException;

import org.springframework.data.domain.Pageable;

public interface MemberNoticeService {

    MemberNoticeAllResDTO memberNoticeAll(MemberNoticeReqDTO req, Pageable pageable)
            throws DateException;
}
