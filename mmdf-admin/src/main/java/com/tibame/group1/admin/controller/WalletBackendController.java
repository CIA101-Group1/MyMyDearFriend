package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.dto.WalletAllResDTO;
import com.tibame.group1.admin.service.WalletRequestService;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.utils.NumberUtils;
import com.tibame.group1.admin.dto.WalletReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@CacheConfig(cacheNames = "member", keyGenerator = "mmdfKeyGenerator")
public class WalletBackendController {

  @Autowired private WalletRequestService walletRequestService;

  @PostMapping("wallets/getAll")
  @Cacheable
  public @ResponseBody ResDTO<WalletAllResDTO> walletGetAll(
      @RequestBody WalletReqDTO req,
      @RequestParam(value = "page", defaultValue = "0") String pageNum,
      @RequestParam(value = "sizePerPage", defaultValue = "10") String sizePerPage) {
    Pageable pageable = PageRequest.of(NumberUtils.toInt(pageNum), NumberUtils.toInt(sizePerPage));
    ResDTO<WalletAllResDTO> res = new ResDTO<>();
    res.setData(walletRequestService.walletAll(req, pageable));
    return res;
  }
}
