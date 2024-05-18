package com.tibame.group1.db.dao;

import com.tibame.group1.db.dto.WalletReqDTO;
import com.tibame.group1.db.entity.WalletHistoryEntity;


public interface WalletHistoryDAO {

    WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId);

    Integer createWalletHistory(WalletReqDTO walletReqDTO);
}