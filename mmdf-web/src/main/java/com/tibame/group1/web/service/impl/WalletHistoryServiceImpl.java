package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.enums.WalletCategory;
import com.tibame.group1.db.dto.WalletQueryParams;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.db.dao.WalletHistoryDAO;
import com.tibame.group1.db.dto.WalletReqDTO;
import com.tibame.group1.web.dto.*;
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
    public List<WalletHistoryEntity> getWallets(WalletQueryParams walletQueryParams) {
        return walletHistoryDAO.getWallets(walletQueryParams);
    }

    @Override
    public Integer payment(PaymentAmountReqDTO paymentAmountReqDTO) {
        WalletReqDTO walletReqDTO = new WalletReqDTO();
        walletReqDTO.setMemberID(paymentAmountReqDTO.getMemberID());
        walletReqDTO.setChangeAmount(paymentAmountReqDTO.getPaymentAmount());
        walletReqDTO.setChangeType(WalletCategory.PAYMENT);
        return walletHistoryDAO.createWalletHistory(walletReqDTO);
    }

    @Override
    public Integer topUp(TopUpAmountReqDTO topUpAmountReqDTO) {
        WalletReqDTO walletReqDTO = new WalletReqDTO();
        walletReqDTO.setMemberID(topUpAmountReqDTO.getMemberID());
        walletReqDTO.setChangeAmount(topUpAmountReqDTO.getTopUpAmount());
        walletReqDTO.setChangeType(WalletCategory.TOP_UP);
        return walletHistoryDAO.createWalletHistory(walletReqDTO);
    }

    @Override
    public Integer deposit(DepositAmountReqDTO depositAmountReqDTO) {
        WalletReqDTO walletReqDTO = new WalletReqDTO();
        walletReqDTO.setMemberID(depositAmountReqDTO.getMemberID());
        walletReqDTO.setChangeAmount(depositAmountReqDTO.getDepositAmount());
        walletReqDTO.setChangeType(WalletCategory.DEPOSIT);
        return walletHistoryDAO.createWalletHistory(walletReqDTO);
    }

    @Override
    public Integer fee(FeeAmountReqDTO feeAmountReqDTO) {
        WalletReqDTO walletReqDTO = new WalletReqDTO();
        walletReqDTO.setMemberID(feeAmountReqDTO.getMemberID());
        walletReqDTO.setChangeAmount(feeAmountReqDTO.getFeeAmount());
        walletReqDTO.setChangeType(WalletCategory.FEE);
        return walletHistoryDAO.createWalletHistory(walletReqDTO);
    }

    @Override
    public Integer withdraw(WithdrawAmountReqDTO withdrawAmountReqDTO) {
        WalletReqDTO walletReqDTO = new WalletReqDTO();
        walletReqDTO.setMemberID(withdrawAmountReqDTO.getMemberID());
        walletReqDTO.setChangeAmount(withdrawAmountReqDTO.getWithdrawAmount());
        walletReqDTO.setChangeType(WalletCategory.WITHDRAW);
        return walletHistoryDAO.createWalletHistory(walletReqDTO);
    }

    @Override
    public Integer market(MarketAmountReqDTO marketAmountReqDTO) {
        WalletReqDTO walletReqDTO = new WalletReqDTO();
        walletReqDTO.setMemberID(marketAmountReqDTO.getMemberID());
        walletReqDTO.setChangeAmount(marketAmountReqDTO.getMarketAmount());
        walletReqDTO.setChangeType(WalletCategory.MARKET);
        return walletHistoryDAO.createWalletHistory(walletReqDTO);
    }
}