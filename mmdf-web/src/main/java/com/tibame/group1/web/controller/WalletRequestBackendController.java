package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.utils.NumberUtils;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.WalletHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@CacheConfig(cacheNames = "member", keyGenerator = "mmdfKeyGenerator")
public class WalletRequestBackendController {

    @Autowired private WalletHistoryService walletHistoryService;

    @PostMapping("wallets/withdraw")
    @CacheEvict(allEntries = true)
    @CheckLogin
    public @ResponseBody ResDTO<WalletWithdrawCreateResDTO> walletWithdraw(
            @Valid @RequestBody WalletWithdrawCreateReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<WalletWithdrawCreateResDTO> res = new ResDTO<>();
        res.setData(walletHistoryService.walletWithdrawCreate(req, loginSource));
        return res;
    }

    @PostMapping("wallets/detail")
    @CacheEvict(allEntries = true)
    @CheckLogin
    public @ResponseBody ResDTO<WalletWithdrawAllResDTO> walletWithdrawAll(
            @RequestBody WalletWithdrawAllReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource,
            @RequestParam(value = "page", defaultValue = "0") String pageNum,
            @RequestParam(value = "sizePerPage", defaultValue = "10") String sizePerPage) {
        Pageable pageable =
                PageRequest.of(NumberUtils.toInt(pageNum), NumberUtils.toInt(sizePerPage));
        ResDTO<WalletWithdrawAllResDTO> res = new ResDTO<>();
        res.setData(walletHistoryService.walletWithdrawAll(req, loginSource, pageable));
        return res;
    }
}
