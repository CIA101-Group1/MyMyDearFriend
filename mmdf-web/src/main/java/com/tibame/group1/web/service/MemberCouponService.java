package com.tibame.group1.web.service;

import com.tibame.group1.db.entity.MemberCouponEntity;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.dto.MemberCouponReqDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberCouponService {

    Integer createCoupon(Integer memberID , MemberCouponReqDTO memberCouponReqDTO);

    List<MemberCouponEntity> getAllMemberCouponByMemberId(LoginSourceDTO loginSource);
}
