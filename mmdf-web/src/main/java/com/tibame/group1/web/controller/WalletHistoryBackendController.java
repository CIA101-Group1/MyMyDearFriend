package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.db.dto.WalletQueryParams;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.WalletHistoryService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
@CacheConfig(cacheNames = "member", keyGenerator = "mmdfKeyGenerator")
public class WalletHistoryBackendController {

    @Autowired private WalletHistoryService walletHistoryService;

    @PostMapping("wallets/create")
    @CacheEvict(allEntries = true)
    @CheckLogin
    public @ResponseBody ResDTO<WalletCreateResDTO> walletCreate(
            @Valid @RequestBody WalletCreateReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<WalletCreateResDTO> res = new ResDTO<>();
        res.setData(walletHistoryService.walletHistoryCreate(req, loginSource));
        return res;
    }

    @CheckLogin
    @GetMapping("/wallets")
    public ResponseEntity<List<WalletHistoryEntity>> getAllWalletHistory(
            @RequestParam(value = "walletCategory", required = false) String walletCategory,
            @RequestParam(value = "search", required = false) String search,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        WalletQueryParams walletQueryParams = new WalletQueryParams();
        walletQueryParams.setWalletCategory(walletCategory);
        walletQueryParams.setSearch(search);
        walletQueryParams.setMemberId(loginSource.getMemberId());

        List<WalletHistoryEntity> walletHistoryEntityList =
                walletHistoryService.getWallets(walletQueryParams);

        return ResponseEntity.status(HttpStatus.OK).body(walletHistoryEntityList);
    }

    @GetMapping("/wallets/{walletID}")
    @CheckLogin
    public ResponseEntity<WalletHistoryEntity> getOneWalletHistory(
            @PathVariable("walletID") Integer walletID,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {

        WalletHistoryEntity wallet = walletHistoryService.getWalletHistoryById(walletID);

        if (wallet != null) {
            return ResponseEntity.status(HttpStatus.OK).body(wallet);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
