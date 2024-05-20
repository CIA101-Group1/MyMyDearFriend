package com.tibame.group1.admin.service;

import com.tibame.group1.db.dto.CouponReqDTO;
import com.tibame.group1.db.entity.CouponEntity;
import org.springframework.stereotype.Service;

@Service
public interface CouponService {


    CouponEntity getCouponByID(Integer couponID);

    Integer createCoupon(CouponReqDTO couponReqDTO);


    void updateCoupon(Integer couponID, CouponReqDTO couponReqDTO);
}
