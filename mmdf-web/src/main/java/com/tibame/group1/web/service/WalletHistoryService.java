package com.tibame.group1.web.service;

import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.db.dto.WalletReqDTO;
import org.springframework.stereotype.Service;


@Service
public interface WalletHistoryService{

    WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId);

    Integer createWalletHistory(WalletReqDTO walletReqDTO);
}