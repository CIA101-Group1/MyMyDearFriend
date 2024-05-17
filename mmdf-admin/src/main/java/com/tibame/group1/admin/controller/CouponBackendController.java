package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.dto.CouponAllReqDTO;
import com.tibame.group1.admin.dto.CouponResDTO;
import com.tibame.group1.admin.service.CouponService;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.admin.CouponCreateReqDTO;
import com.tibame.group1.common.dto.admin.CouponCreateResDTO;
import com.tibame.group1.common.exception.DateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class CouponBackendController {

    @Autowired
    private CouponService couponService;

    @PostMapping("coupon/create")
    public @ResponseBody ResDTO<CouponCreateResDTO> couponCreate(@RequestBody CouponCreateReqDTO req) throws DateException {

        ResDTO<CouponCreateResDTO> res = new ResDTO<>();
        System.out.println("有傳輸成功");
        res.setData(couponService.couponCreate(req));
        System.out.println("有傳輸成功01");
        return res;
    }

    @RequestMapping("coupon/all")
    public @ResponseBody ResDTO<CouponResDTO> couponAll(
            @RequestBody CouponAllReqDTO req,
            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "sizePerPage", defaultValue = "10") int sizePerPage)throws DateException {
        Pageable pageable = PageRequest.of(pageNum, sizePerPage);
        ResDTO<CouponResDTO> res = new ResDTO<>();
        res.setData(couponService.couponAll(req, pageable));
        return res;
    }
}
