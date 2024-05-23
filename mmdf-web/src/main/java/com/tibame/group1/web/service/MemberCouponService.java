package com.tibame.group1.web.service;

import com.tibame.group1.web.dto.MemberCouponReqDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberCouponService {

    Integer createCoupon(Integer memberID , MemberCouponReqDTO memberCouponReqDTO);
}
