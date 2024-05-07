package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.service.CouponService;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.admin.CouponCreateReqDTO;
import com.tibame.group1.common.dto.admin.CouponCreateResDTO;
import com.tibame.group1.common.exception.DateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mmdf/web/api/")
public class CouponBackendController {

    @Autowired
    private CouponService couponService;

    @PostMapping("coupon/create")
    public @ResponseBody ResDTO<CouponCreateResDTO> couponCreate(@RequestBody CouponCreateReqDTO req) throws DateException {
        ResDTO<CouponCreateResDTO> res = new ResDTO<>();
        res.setData(couponService.couponCreate(req));
        return res;
    }
}
