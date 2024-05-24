package com.tibame.group1.web.service;

import com.tibame.group1.db.dto.WalletQueryParams;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.db.dto.WalletReqDTO;
import com.tibame.group1.web.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface WalletHistoryService{

    WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId);

    Integer createWalletHistory(WalletReqDTO walletReqDTO);

    List<WalletHistoryEntity> getWallets(WalletQueryParams walletQueryParams);

    Integer topUp(TopUpAmountReqDTO topUpAmountReqDTO);

    Integer payment(PaymentAmountReqDTO paymentAmountReqDTO);

    Integer deposit(DepositAmountReqDTO depositAmountReqDTO);

    Integer fee(FeeAmountReqDTO feeAmountReqDTO);

    Integer withdraw(WithdrawAmountReqDTO withdrawAmountReqDTO);

    Integer market(MarketAmountReqDTO marketAmountReqDTO);

}