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

  @CheckLogin
  @PutMapping("/coupons/{couponID}")
  public ResponseEntity<CouponEntity> updateCoupon(
          @PathVariable("couponID") Integer couponID,
          @RequestBody @Valid CouponReqDTO couponReqDTO,
          @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource){

    // 先檢查Coupon是否存在
    CouponEntity coupon = couponService.getCouponByID(couponID);

    if (coupon == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 修改優惠卷的數據
    couponService.updateCoupon(couponID, couponReqDTO);

    CouponEntity updateCoupon = couponService.getCouponByID(couponID);

    return ResponseEntity.status(HttpStatus.OK).body(updateCoupon);
  }

  @CheckLogin
  @DeleteMapping("/coupons/{couponID}")
  public ResponseEntity<CouponEntity> deleteCoupon(
          @PathVariable("couponID") Integer couponID,
          @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource){

    couponService.deleteCouponByID(couponID);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
