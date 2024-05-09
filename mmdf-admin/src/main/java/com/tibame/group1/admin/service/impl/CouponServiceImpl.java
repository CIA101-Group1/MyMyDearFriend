package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.service.CouponService;
import com.tibame.group1.common.dto.admin.CouponCreateReqDTO;
import com.tibame.group1.common.dto.admin.CouponCreateResDTO;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.common.utils.DateUtils;
import com.tibame.group1.db.entity.CouponEntity;
import com.tibame.group1.db.repository.CouponRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = Exception.class)
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public CouponCreateResDTO couponCreate(CouponCreateReqDTO req) throws DateException {
        CouponEntity coupon  = new CouponEntity();
        coupon.setTitle(req.getTitle());
        coupon.setLowPrice(Integer.valueOf(req.getLowPrice()));
        coupon.setDiscount(Integer.valueOf(req.getDiscount()));
        coupon.setNumber(Integer.valueOf(req.getNumber()));
        coupon.setDateStart(DateUtils.stringToDate(req.getDateStart(),DateUtils.DEFAULT_WEB_DATE_FORMAT));
        coupon.setDateEnd(DateUtils.stringToDate(req.getDateEnd(),DateUtils.DEFAULT_WEB_DATE_FORMAT));
        coupon.setAddable(Integer.valueOf(req.getAddable()));
        coupon.setLivemode(Integer.valueOf(req.getLivemode()));

    coupon = couponRepository.save(coupon);
    CouponCreateResDTO res = new CouponCreateResDTO();
    res.setCouponId(String.valueOf(coupon.getCouponID()));
    return res;
    }
}
