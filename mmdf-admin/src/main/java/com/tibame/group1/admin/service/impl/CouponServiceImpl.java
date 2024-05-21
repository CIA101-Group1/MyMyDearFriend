package com.tibame.group1.admin.service.Impl;

import com.tibame.group1.admin.service.CouponService;
import com.tibame.group1.db.dao.CouponDAO;
import com.tibame.group1.db.dto.CouponReqDTO;
import com.tibame.group1.db.entity.CouponEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDAO couponDAO;

    @Override
    public CouponEntity getCouponByID(Integer couponID) {
        return couponDAO.getCouponByID(couponID);
    }

    @Override
    public Integer createCoupon(CouponReqDTO couponReqDTO) {

        return couponDAO.createCoupon(couponReqDTO);
    }

    @Override
    public void updateCoupon(Integer couponID, CouponReqDTO couponReqDTO) {
        couponDAO.updateCoupon(couponID, couponReqDTO);
    }

    @Override
    public void deleteCouponByID(Integer couponID) {
        couponDAO.deleteCouponByID(couponID);
    }
}
