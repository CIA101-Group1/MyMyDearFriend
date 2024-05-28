package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.annotation.CheckLogin;
import com.tibame.group1.admin.dto.WalletWithdrawUpdateReqDTO;
import com.tibame.group1.admin.dto.WalletWithdrawUpdateResDTO;
import com.tibame.group1.admin.service.WalletRequestService;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WalletWithdrawUpdateBackendController {

    @Autowired private WalletRequestService walletRequestService;

    @PostMapping("wallets/withdrawUpdate")
    @CheckLogin
    public @ResponseBody ResDTO<WalletWithdrawUpdateResDTO> withdrawUpdate(
            @Valid @RequestBody WalletWithdrawUpdateReqDTO req) throws CheckRequestErrorException {
        ResDTO<WalletWithdrawUpdateResDTO> res = new ResDTO<>();
        res.setData(walletRequestService.withdrawUpdate(req));
        return res;
    }
}
