package com.tibame.group1.db.dao;

import com.tibame.group1.db.entity.CouponEntity;
import com.tibame.group1.db.entity.MemberCouponEntity;

public interface MemberCouponDAO {

    CouponEntity findCouponById(Integer couponID);

    Integer saveMemberCoupon(MemberCouponEntity memberCouponEntity);

    MemberCouponEntity findLatestMemberCoupon();
}
