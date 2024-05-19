package com.tibame.group1.web.controller;

import com.tibame.group1.common.enums.WalletCategory;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.db.dto.WalletReqDTO;
import com.tibame.group1.web.service.WalletHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WalletHistoryBachendController {

  @Autowired private WalletHistoryService walletHistoryService;

  @CheckLogin
  @GetMapping("/wallets")
  public ResponseEntity<List<WalletHistoryEntity>> getAllWalletHistory(
          @RequestParam(value = "walletCategory", required = false) WalletCategory walletCategory,
          @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
    List<WalletHistoryEntity> walletHistoryEntityList =
            walletHistoryService.getWallets(walletCategory);

    return ResponseEntity.status(HttpStatus.OK).body(walletHistoryEntityList);
  }


  /** todo: 錢包分頁功能與每筆細項，用Lsit */
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
