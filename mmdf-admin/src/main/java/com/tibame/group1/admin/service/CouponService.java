package com.tibame.group1.admin.service;

import com.tibame.group1.db.dto.CouponQueryParams;
import com.tibame.group1.db.dto.CouponReqDTO;
import com.tibame.group1.db.entity.CouponEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CouponService {

    List<CouponEntity> getCoupons(CouponQueryParams couponQueryParams);

    CouponEntity getCouponByID(Integer couponID);

    Integer createCoupon(CouponReqDTO couponReqDTO);


    void updateCoupon(Integer couponID, CouponReqDTO couponReqDTO);

    void deleteCouponByID(Integer couponID);

}
