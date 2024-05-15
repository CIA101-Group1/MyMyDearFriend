package com.tibame.group1.web.controller;

import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.web.service.WalletHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WalletHistoryBackendController {

    @Autowired
    private WalletHistoryService walletHistoryService;

    @GetMapping("wallets/{walletID}")
    public ResponseEntity<WalletHistoryEntity> getWalletHistory(@PathVariable Integer walletID){
        WalletHistoryEntity wallet = walletHistoryService.getWalletHistoryById(walletID);

        if(wallet != null){
            return ResponseEntity.status(HttpStatus.OK).body(wallet);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
