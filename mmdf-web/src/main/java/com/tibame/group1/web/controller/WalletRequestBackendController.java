package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.dto.WalletWithdrawReqDTO;
import com.tibame.group1.web.dto.WalletWithdrawResDTO;
import com.tibame.group1.web.service.WalletHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@CacheConfig(cacheNames = "member", keyGenerator = "mmdfKeyGenerator")
public class WalletRequestBackendController {

  @Autowired private WalletHistoryService walletHistoryService;

  @PostMapping("wallets/withdraw")
  @CacheEvict(allEntries = true)
  @CheckLogin
  public @ResponseBody ResDTO<WalletWithdrawResDTO> walletWithdraw(
      @Valid @RequestBody WalletWithdrawReqDTO req,
      @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
    ResDTO<WalletWithdrawResDTO> res = new ResDTO<>();
    res.setData(walletHistoryService.walletWithdrawCreate(req, loginSource));
    return res;
  }


}
