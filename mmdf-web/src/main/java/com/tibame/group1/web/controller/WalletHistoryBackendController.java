package com.tibame.group1.web.controller;

import com.tibame.group1.common.enums.WalletCategory;
import com.tibame.group1.db.dto.WalletQueryParams;
import com.tibame.group1.db.dto.WalletReqDTO;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.WalletHistoryService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WalletHistoryBackendController {

    @Autowired private WalletHistoryService walletHistoryService;

    // 付款
    @CheckLogin
    @PostMapping("/wallets/payment")
    public ResponseEntity<WalletHistoryEntity> payment(
            @RequestBody @Valid PaymentAmountReqDTO paymentAmountReqDTO,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {

        Integer walletID = walletHistoryService.payment(paymentAmountReqDTO);

        WalletHistoryEntity wallet = walletHistoryService.getWalletHistoryById(walletID);

        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }


    // 儲值
    @CheckLogin
    @PostMapping("/wallets/top-up")
    public ResponseEntity<WalletHistoryEntity> topUp(
            @RequestBody @Valid TopUpAmountReqDTO topUpAmountReqDTO,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {

        Integer walletID = walletHistoryService.topUp(topUpAmountReqDTO);

        WalletHistoryEntity wallet = walletHistoryService.getWalletHistoryById(walletID);

        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }

    // 入帳
    @CheckLogin
    @PostMapping("/wallets/deposit")
    public ResponseEntity<WalletHistoryEntity> deposit(
            @RequestBody @Valid DepositAmountReqDTO depositAmountReqDTO,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {

        Integer walletID = walletHistoryService.deposit(depositAmountReqDTO);

        WalletHistoryEntity wallet = walletHistoryService.getWalletHistoryById(walletID);

        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }

    // 手續費
    @CheckLogin
    @PostMapping("/wallets/fee")
    public ResponseEntity<WalletHistoryEntity> fee(
            @RequestBody @Valid FeeAmountReqDTO feeAmountReqDTO,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {

        Integer walletID = walletHistoryService.fee(feeAmountReqDTO);

        WalletHistoryEntity wallet = walletHistoryService.getWalletHistoryById(walletID);

        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }

    // 提款
    @CheckLogin
    @PostMapping("/wallets/withdraw")
    public ResponseEntity<WalletHistoryEntity> withdraw(
            @RequestBody @Valid WithdrawAmountReqDTO withdrawAmountReqDTO,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {

        Integer walletID = walletHistoryService.withdraw(withdrawAmountReqDTO);

        WalletHistoryEntity wallet = walletHistoryService.getWalletHistoryById(walletID);

        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }

    //市集報名費
    @CheckLogin
    @PostMapping("/wallets/market")
    public ResponseEntity<WalletHistoryEntity> market(
            @RequestBody @Valid MarketAmountReqDTO marketAmountReqDTO,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {

        Integer walletID = walletHistoryService.market(marketAmountReqDTO);

        WalletHistoryEntity wallet = walletHistoryService.getWalletHistoryById(walletID);

        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }

    /** todo: 錢包分頁功能與每筆細項，用List */

    @CheckLogin
    @GetMapping("/wallets")
    public ResponseEntity<List<WalletHistoryEntity>> getAllWalletHistory(
            @RequestParam(value = "walletCategory", required = false) WalletCategory walletCategory,
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

    @PostMapping("/wallets")
    @CheckLogin
    public ResponseEntity<WalletHistoryEntity> createWalletHistory(
            @RequestBody @Valid WalletReqDTO walletReqDTO,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {

        Integer walletID = walletHistoryService.createWalletHistory(walletReqDTO);

        WalletHistoryEntity wallet = walletHistoryService.getWalletHistoryById(walletID);

        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }
}
