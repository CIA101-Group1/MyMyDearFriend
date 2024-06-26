package com.tibame.group1.db.dao;

import com.tibame.group1.db.dto.WalletQueryParams;
import com.tibame.group1.db.entity.WalletHistoryEntity;

import java.util.List;

public interface WalletHistoryDAO {

    WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId);

    List<WalletHistoryEntity> getWallets(WalletQueryParams walletQueryParams);
}
