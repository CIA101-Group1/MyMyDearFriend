package com.tibame.group1.db.dao;

import com.tibame.group1.db.dto.CouponReqDTO;
import com.tibame.group1.db.entity.CouponEntity;


public interface CouponDAO {

    CouponEntity getCouponByID(Integer couponID);

    Integer createCoupon(CouponReqDTO couponReqDTO);

}
