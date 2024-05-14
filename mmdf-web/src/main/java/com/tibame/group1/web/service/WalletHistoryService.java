package com.tibame.group1.web.service;


import com.tibame.group1.db.entity.WalletHistoryEntity;
import org.apache.commons.math3.stat.descriptive.summary.Product;

public interface WalletHistoryService {

    WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId);
}
