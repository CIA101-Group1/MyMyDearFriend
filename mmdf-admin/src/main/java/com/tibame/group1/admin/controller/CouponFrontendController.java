package com.tibame.group1.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mmdf/admin/")
public class CouponFrontendController {

    @GetMapping("coupon/create")
    public String couponCreate() {
        return "coupon-create";
    }
}
