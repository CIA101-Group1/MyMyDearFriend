package com.tibame.group1.db.dao;

import com.tibame.group1.db.dto.CouponQueryParams;
import com.tibame.group1.db.dto.CouponReqDTO;
import com.tibame.group1.db.entity.CouponEntity;

import java.util.List;


public interface CouponDAO {

    List<CouponEntity> getCoupons(CouponQueryParams couponQueryParams);

    CouponEntity getCouponByID(Integer couponID);

    Integer createCoupon(CouponReqDTO couponReqDTO);

    void updateCoupon(Integer couponID, CouponReqDTO couponReqDTO);

    void deleteCouponByID(Integer couponID);
}
