package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidOrderEntity;
import com.tibame.group1.db.entity.BidProductConditionEntity;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.BidOrderService;
import com.tibame.group1.web.service.BidProductConditionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BidOrderApiController {

    @Autowired
    BidOrderService bidOrderService;

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
    public ResDTO<BidOrderEntity> findById(@PathVariable("oderId") Integer oderId) throws CheckRequestErrorException {
        ResDTO<BidOrderEntity> res = new ResDTO<>();
        BidOrderEntity bidOrderEntity = bidOrderService.findById(oderId);
        res.setData(bidOrderEntity);
        return res;
    }

    // updateForPaid
    // updateForDeliver
    // updateForFinish

}
