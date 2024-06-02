package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.MarketService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CacheConfig(cacheNames = "member", keyGenerator = "mmdfKeyGenerator")
public class MarketBackendController {
    @Autowired private MarketService marketService;

    @GetMapping("/market")
    public @ResponseBody ResDTO<List<MarketResDTO>> getMarketByStatus() {
        ResDTO<List<MarketResDTO>> res = new ResDTO<>();
        res.setData(marketService.getMarketByStatus());
        return res;
    }

    @GetMapping("/market/detailById")
    public @ResponseBody ResDTO<MarketResDTO> marketDetail(
            @RequestParam(value = "marketId") Integer marketId) throws CheckRequestErrorException {
        ResDTO<MarketResDTO> res = new ResDTO<>();
        res.setData(marketService.marketDetail(marketId));
        return res;
    }

    @PostMapping("/market/register")
    @CheckLogin(isVerified = false)
    @CacheEvict(allEntries = true)
    public @ResponseBody ResDTO<MemberRegistrationResDTO> registerMemberToMarket(
            @Valid @RequestBody MarketRegistrationReqDTO marketRegistrationReq,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        ResDTO<MemberRegistrationResDTO> res = new ResDTO<>();
        res.setData(marketService.registerMemberToMarket(marketRegistrationReq,loginSource));
        return res;
    }

    //取消報名
    @PostMapping("/market/cancel")
    @CheckLogin(isVerified = false)
    @CacheEvict(allEntries = true)
    public @ResponseBody ResDTO<MarketCancelResDTO> cancelRegistration(
            @Valid @RequestBody MarketRegistrationReqDTO marketRegistrationReq,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE)LoginSourceDTO loginSource
    ) throws CheckRequestErrorException{
        ResDTO<MarketCancelResDTO> res = new ResDTO<>();
        res.setData(marketService.cancelRegistration(marketRegistrationReq,loginSource));
        return res;
    }


    @GetMapping("/market/allRegister")
    @CheckLogin(isVerified = true)
    public @ResponseBody ResDTO<List<MarketRegistrationResDTO>> findAllByMemberId(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
         {
        ResDTO<List<MarketRegistrationResDTO>> res = new ResDTO<>();
        res.setData(marketService.findAllByMemberId(loginSource));
        return res;
    }
}
