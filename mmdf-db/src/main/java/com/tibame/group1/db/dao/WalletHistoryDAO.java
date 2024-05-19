package com.tibame.group1.db.dao;

import com.tibame.group1.common.enums.WalletCategory;
import com.tibame.group1.db.dto.WalletReqDTO;
import com.tibame.group1.db.entity.WalletHistoryEntity;

import java.util.List;


public interface WalletHistoryDAO {

    WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId);

    Integer createWalletHistory(WalletReqDTO walletReqDTO);

    List<WalletHistoryEntity> getWallets(WalletCategory walletCategory);
}