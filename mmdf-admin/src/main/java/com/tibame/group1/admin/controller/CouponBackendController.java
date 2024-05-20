package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.annotation.CheckLogin;
import com.tibame.group1.admin.dto.AdminLoginSourceDTO;
import com.tibame.group1.admin.service.CouponService;
import com.tibame.group1.db.dto.CouponReqDTO;
import com.tibame.group1.db.entity.CouponEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CouponBackendController {

  @Autowired private CouponService couponService;

  @GetMapping("/coupons/{couponID}")
  public ResponseEntity<CouponEntity> getOneCoupon(
          @PathVariable("couponID") Integer couponID,
          @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource) {

    CouponEntity coupon = couponService.getCouponByID(couponID);

    if (coupon != null) {
      return ResponseEntity.status(HttpStatus.OK).body(coupon);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

//  @PostMapping("coupons/create")
  @CheckLogin
  @PostMapping("/coupons")
  public ResponseEntity<CouponEntity> createCoupon(
          @RequestBody @Valid CouponReqDTO couponReqDTO,
          @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource) {

    Integer couponID = couponService.createCoupon(couponReqDTO);

    CouponEntity coupon = couponService.getCouponByID(couponID);

    return ResponseEntity.status(HttpStatus.CREATED).body(coupon);
  }
}
