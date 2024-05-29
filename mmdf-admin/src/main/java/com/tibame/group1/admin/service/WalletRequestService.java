package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.WalletAllResDTO;
import com.tibame.group1.admin.dto.WalletReqDTO;
import com.tibame.group1.admin.dto.WalletWithdrawUpdateReqDTO;
import com.tibame.group1.admin.dto.WalletWithdrawUpdateResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import org.springframework.data.domain.Pageable;

public interface WalletRequestService {
  WalletAllResDTO walletAll(WalletReqDTO req, Pageable pageable);

  WalletWithdrawUpdateResDTO withdrawUpdate(WalletWithdrawUpdateReqDTO req) throws CheckRequestErrorException;
}
