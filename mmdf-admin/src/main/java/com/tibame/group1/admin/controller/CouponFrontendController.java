package com.tibame.group1.admin.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.web.MemberDetailResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("mmdf/admin/")
public class CouponFrontendController {

    @GetMapping("coupon/create")
    public String couponCreate() {
        return "coupon-create";
    }

    @GetMapping("coupon/all")
    public String couponAll() {
        return "coupon-all";
    }

}
