package com.tibame.group1.web.controller;

import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.WalletHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class WalletHistoryBackendController {

    @Autowired
    private WalletHistoryService walletHistoryService;

    /**
     * todo: 錢包分頁功能與每筆細項，用Lsit
     * */
    @GetMapping("/wallets/{walletID}")
    @CheckLogin
    public ResponseEntity<WalletHistoryEntity> getWalletHistory(@PathVariable("walletID") Integer walletID,
                                                                @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource){

        WalletHistoryEntity wallet = walletHistoryService.getWalletHistoryById(walletID);

        if(wallet != null){
            return ResponseEntity.status(HttpStatus.OK).body(wallet);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/wallets")
    public ResponseEntity<WalletHistoryEntity> createWalletHistory(@PathVariable("walletID") Integer walletID,
                                                                @RequestAttribute(LoginSourceDTO.ATTRIBUTE)LoginSourceDTO loginSource){
        return null;
    }
}
