package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.MemberAllReqDTO;
import com.tibame.group1.admin.dto.MemberResDTO;

import org.springframework.data.domain.Pageable;

public interface MemberService {
    MemberResDTO memberAll(MemberAllReqDTO req, Pageable pageable);
}
