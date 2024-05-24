package com.tibame.group1.web.controller;

import com.tibame.group1.web.dto.MemberCouponReqDTO;
import com.tibame.group1.web.service.MemberCouponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberCouponBackendController {

    @Autowired
    private MemberCouponService memberCouponService;

    @PostMapping("/users/{memberID}/coupons")
    public ResponseEntity<Integer> createCoupon(
            @PathVariable("memberID") Integer memberID,
            @RequestBody @Valid MemberCouponReqDTO memberCouponReqDTO) {

        Integer serialCouponID = memberCouponService.createCoupon(memberID , memberCouponReqDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(serialCouponID);
    }

}
