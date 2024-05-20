package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.AdminLoginSourceDTO;
import com.tibame.group1.admin.dto.MemberReqDTO;
import com.tibame.group1.admin.dto.MemberAllResDTO;

import org.springframework.data.domain.Pageable;

public interface MemberService {
    MemberAllResDTO memberAll(
            MemberReqDTO req, AdminLoginSourceDTO adminLoginSource, Pageable pageable);
}
