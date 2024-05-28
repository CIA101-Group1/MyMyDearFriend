package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.WalletAllResDTO;
import com.tibame.group1.admin.dto.WalletReqDTO;
import org.springframework.data.domain.Pageable;

public interface WalletRequestService {
  WalletAllResDTO walletAll(WalletReqDTO req, Pageable pageable);
}
