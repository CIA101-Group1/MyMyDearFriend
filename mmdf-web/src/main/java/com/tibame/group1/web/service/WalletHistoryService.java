package com.tibame.group1.web.service;

import com.tibame.group1.db.dto.WalletQueryParams;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.web.dto.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WalletHistoryService {

  WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId);

  WalletCreateResDTO walletHistoryCreate(WalletCreateReqDTO req, LoginSourceDTO loginSource);

  List<WalletHistoryEntity> getWallets(WalletQueryParams walletQueryParams);

  WalletWithdrawResDTO walletWithdrawCreate(WalletWithdrawReqDTO req, LoginSourceDTO loginSource);
}
