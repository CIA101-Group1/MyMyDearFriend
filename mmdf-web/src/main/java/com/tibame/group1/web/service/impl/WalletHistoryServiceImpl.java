package com.tibame.group1.web.service.impl;

import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.db.dao.WalletHistoryDAO;
import com.tibame.group1.web.service.WalletHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WalletHistoryServiceImpl implements WalletHistoryService {

    @Autowired
    private WalletHistoryDAO walletHistoryDAO;

    @Override
    public WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId) {
        return walletHistoryDAO.getWalletHistoryById(walletHistoryId);
    }
}