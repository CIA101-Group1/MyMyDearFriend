package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.annotation.CheckLogin;
import com.tibame.group1.admin.dto.AdminLoginSourceDTO;
import com.tibame.group1.admin.service.CouponService;
import com.tibame.group1.common.enums.CouponEffectCategory;
import com.tibame.group1.common.enums.CouponStackCategory;
import com.tibame.group1.common.utils.Page;
import com.tibame.group1.db.dto.CouponQueryParams;
import com.tibame.group1.db.dto.CouponReqDTO;
import com.tibame.group1.db.entity.CouponEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CouponBackendController {

  @Autowired private CouponService couponService;

  @CheckLogin
  @GetMapping("/coupons")
  public ResponseEntity<Page<CouponEntity>> getCoupons(

          @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource,

          // 查詢 Filtering
          @RequestParam(value = "couponStackCategory", required = false) CouponStackCategory couponStackCategory,
          @RequestParam(value = "couponEffectCategory", required = false) CouponEffectCategory couponEffectCategory,
          @RequestParam(value = "search", required = false) String search,

          // 排序 Sorting
          @RequestParam(value = "orderBy", defaultValue = "date_start") String orderBy,
          @RequestParam(value = "sort", defaultValue = "desc") String sort,

          // 分頁 Pagination
          @RequestParam(value = "limit", defaultValue = "10") @Max(1000) @Min(0) Integer limit,
          @RequestParam(value = "offset", defaultValue = "0") @Max(0) @Min(0) Integer offset
  ) {
    CouponQueryParams couponQueryParams = new CouponQueryParams();
    couponQueryParams.setCouponStackCategory(couponStackCategory);
    couponQueryParams.setCouponEffectCategory(couponEffectCategory);
    couponQueryParams.setSearch(search);
    couponQueryParams.setOrderBy(orderBy);
    couponQueryParams.setSort(sort);
    couponQueryParams.setLimit(limit);
    couponQueryParams.setOffset(offset);

    List<CouponEntity> couponList = couponService.getCoupons(couponQueryParams);

    Integer total = couponService.countCoupon(couponQueryParams);

    Page<CouponEntity> page = new Page<>();
    page.setLimit(limit);
    page.setOffset(offset);
    page.setTotal(total);
    page.setResults(couponList);

    return ResponseEntity.status(HttpStatus.OK).body(page);
  }

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
