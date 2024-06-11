package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.web.BidOrderPayReqDTO;
import com.tibame.group1.common.dto.web.MemberEditReqDTO;
import com.tibame.group1.common.enums.BidOrderStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.db.entity.BidOrderEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.BidOrderService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CacheConfig(cacheNames = "member", keyGenerator = "mmdfKeyGenerator")
public class BidOrderApiController {

    @Autowired BidOrderService bidOrderService;

    @GetMapping("/seller/bidorder")
    @CheckLogin(isVerified = false)
    public ResDTO<List<BidOrderEntity>> findAllForSeller(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<List<BidOrderEntity>> res = new ResDTO<>();
        res.setData(bidOrderService.findBidOrdersForSeller(loginSource));
        return res;
    }

    @GetMapping("/buyer/bidorder")
    @CheckLogin(isVerified = false)
    public ResDTO<List<BidOrderEntity>> findAllForBuyer(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<List<BidOrderEntity>> res = new ResDTO<>();
        res.setData(bidOrderService.findBidOrdersForBuyer(loginSource));
        return res;
    }

    @GetMapping("/bidorder/{oderId}")
    public ResDTO<BidOrderEntity> findById(@PathVariable("oderId") Integer oderId)
            throws CheckRequestErrorException {
        ResDTO<BidOrderEntity> res = new ResDTO<>();
        BidOrderEntity bidOrderEntity = bidOrderService.findById(oderId);
        res.setData(bidOrderEntity);
        return res;
    }

    // updateForPaid
    @PutMapping("/bidorder/pay/{orderId}")
    @CheckLogin(isVerified = false)
    @CacheEvict(allEntries = true)
    public @ResponseBody ResDTO<?> payBidOrder(
            @PathVariable("orderId") Integer orderId,
            @Valid @RequestBody BidOrderPayReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws DateException, IOException, CheckRequestErrorException {

        bidOrderService.payBidOrder(orderId, req, loginSource);
        return new ResDTO<>();
    }

    
    // updateForDeliver

    @PutMapping("/bidorder/{orderId}")
    @CheckLogin(isVerified = false)
    @CacheEvict(allEntries = true)
    public @ResponseBody ResDTO<?> updateBidOrderStatus(
            @PathVariable("orderId") Integer orderId,
            @RequestParam("status") BidOrderStatus newStatus)
            throws DateException, IOException, CheckRequestErrorException {

        bidOrderService.updateBidOrderStatus(orderId, newStatus);
        return new ResDTO<>();
    }
}
