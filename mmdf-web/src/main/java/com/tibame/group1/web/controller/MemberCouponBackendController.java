package com.tibame.group1.web.controller;

import com.tibame.group1.db.entity.MemberCouponEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.dto.MemberCouponReqDTO;
import com.tibame.group1.web.service.MemberCouponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/api/users/coupons")
    @CheckLogin
    public ResponseEntity<List<MemberCouponEntity>> getAllMemberCouponByMemberId(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {

        List<MemberCouponEntity> memberCouponEntities= memberCouponService.getAllMemberCouponByMemberId(loginSource);

        return ResponseEntity.status(HttpStatus.OK).body(memberCouponEntities);
    }

}
