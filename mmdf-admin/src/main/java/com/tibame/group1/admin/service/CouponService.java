package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.CouponAllReqDTO;
import com.tibame.group1.admin.dto.CouponResDTO;
import com.tibame.group1.admin.dto.MemberAllReqDTO;
import com.tibame.group1.admin.dto.MemberResDTO;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.admin.CouponCreateReqDTO;
import com.tibame.group1.common.dto.admin.CouponCreateResDTO;
import com.tibame.group1.common.exception.DateException;
import org.springframework.data.domain.Pageable;

public interface CouponService {
    CouponCreateResDTO couponCreate(CouponCreateReqDTO req) throws DateException;

    CouponResDTO couponAll(CouponAllReqDTO req, Pageable pageable) throws DateException;
}
