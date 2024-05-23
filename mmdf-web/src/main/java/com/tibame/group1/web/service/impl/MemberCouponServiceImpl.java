package com.tibame.group1.web.service.impl;


import com.tibame.group1.db.dao.MemberCouponDAO;
import com.tibame.group1.db.entity.CouponEntity;
import com.tibame.group1.common.enums.CouponEffectCategory;
import com.tibame.group1.db.entity.MemberCouponEntity;
import com.tibame.group1.web.dto.MemberCouponItem;
import com.tibame.group1.web.dto.MemberCouponReqDTO;
import com.tibame.group1.web.service.MemberCouponService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MemberCouponServiceImpl implements MemberCouponService {

    @Autowired
    private MemberCouponDAO memberCouponDAO;


    //創建訂單
    /**
     * 這邊的Usetime雖然是預設紀錄當下時間
     * 但實際應該是實體那邊要設為可以Null
     * 當會員使用優惠卷，用修改的方式紀錄當下使用時間*/
    @Transactional
    @Override
    public Integer createCoupon(Integer memberID, MemberCouponReqDTO memberCouponReqDTO) {

        Integer lastSerialCouponID = null;

        for (MemberCouponItem item : memberCouponReqDTO.getMemberCouponList()) {
            Integer couponID = item.getCouponID();
            CouponEntity couponEntity = memberCouponDAO.findCouponById(couponID);

            if (couponEntity == null) {
                throw new IllegalArgumentException("Coupon ID " + couponID + " 不存在 ");
            }

            if (couponEntity.getLivemode() != CouponEffectCategory.EFFECTIVE) {
                throw new IllegalArgumentException("Coupon ID " + couponID + " 此張優惠卷失效 ");
            }

            MemberCouponEntity memberCouponEntity = new MemberCouponEntity();
            Date now = new Date();
            memberCouponEntity.setMemberID(memberID);
            memberCouponEntity.setCouponID(couponID);
            memberCouponEntity.setStack(item.getStack());
            memberCouponEntity.setIsUsed(false);
            memberCouponEntity.setGetTime(new Date());
            memberCouponEntity.setUseTime(new Date());

            memberCouponDAO.saveMemberCoupon(memberCouponEntity);
            lastSerialCouponID = memberCouponEntity.getSerialCouponID();
        }


    return lastSerialCouponID;
}
}