package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.enums.WalletCategory;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.db.dao.WalletHistoryDAO;
import com.tibame.group1.db.dto.WalletReqDTO;
import com.tibame.group1.web.service.WalletHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WalletHistoryServiceImpl implements WalletHistoryService {

    @Autowired
    private WalletHistoryDAO walletHistoryDAO;

    @Override
    public WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId) {
        return walletHistoryDAO.getWalletHistoryById(walletHistoryId);
    }

    @Override
    public Integer createWalletHistory(WalletReqDTO walletReqDTO) {
        return walletHistoryDAO.createWalletHistory(walletReqDTO);
    }

    @Override
    public List<WalletHistoryEntity> getWallets(WalletCategory walletCategory, String search) {
        return walletHistoryDAO.getWallets(walletCategory, search);
    }
}