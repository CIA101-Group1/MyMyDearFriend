package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.web.BidAddReqDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.db.entity.BidEntity;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.BidProductService;
import com.tibame.group1.web.service.BidService;

import com.tibame.group1.web.service.impl.BidProductServiceImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BidApiController {

    @Autowired private BidService bidService;
    @Autowired private BidProductService bidProductService;

    @PostMapping("/bid")
    @CheckLogin(isVerified = false)
    public @ResponseBody ResDTO<?> addBid(
            @Valid BidAddReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws DateException, IOException, CheckRequestErrorException {
        bidService.addBid(req, loginSource);
        return new ResDTO<>();
    }

    @GetMapping("/bidproduct/{productId}/bid")
    public ResDTO<List<BidEntity>> getBidsByProduct(@PathVariable("productId") Integer productId) throws CheckRequestErrorException {
        ResDTO<List<BidEntity>> res = new ResDTO<>();
        BidProductEntity product = bidProductService.findById(productId);
        if (product == null) {
            throw new CheckRequestErrorException("商品編號：查無此商品資料");
        }
        res.setData(bidService.findBidsByProductId(productId));
        return res;
    }

    @GetMapping("/bidproduct/{productId}/currentPrice")
    public ResDTO<Integer> getCurrentPriceForProduct(@PathVariable("productId") Integer productId) throws CheckRequestErrorException {
        ResDTO<Integer> res = new ResDTO<>();
        res.setData(bidService.findCurrentPriceForProduct(productId));
        return res;
    }
}
