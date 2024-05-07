package com.tibame.group1.admin.service;

import com.tibame.group1.common.dto.admin.CouponCreateReqDTO;
import com.tibame.group1.common.dto.admin.CouponCreateResDTO;
import com.tibame.group1.common.exception.DateException;

public interface CouponService {
    CouponCreateResDTO couponCreate(CouponCreateReqDTO req) throws DateException;
}
