package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.MemberAllResDTO;
import com.tibame.group1.admin.dto.MemberReqDTO;

import org.springframework.data.domain.Pageable;

public interface MemberService {
    MemberAllResDTO memberAll(MemberReqDTO req, Pageable pageable);
}
