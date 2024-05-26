package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.enums.WalletCategory;
import com.tibame.group1.db.dto.WalletQueryParams;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.db.dao.WalletHistoryDAO;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.db.repository.WalletHistoryRepository;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.WalletHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class WalletHistoryServiceImpl implements WalletHistoryService {

  @Autowired private WalletHistoryDAO walletHistoryDAO;

  @Autowired private WalletHistoryRepository walletHistoryRepository;

  @Autowired private MemberRepository memberRepository;

  @Override
  public WalletCreateResDTO walletHistoryCreate(
      WalletCreateReqDTO req, LoginSourceDTO loginSource) {

    // 根據 LoginSourceDTO 中的 memberId 獲取會員資料
    Integer memberId = loginSource.getMemberId();
    MemberEntity member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));

    // 計算新的 walletAmount
    int newWalletAmount;
    int categoryCode = req.getWalletCategory().getCode();
    if (categoryCode == WalletCategory.TOP_UP.getCode()
        || categoryCode == WalletCategory.DEPOSIT.getCode()) {
      newWalletAmount = member.getWalletAmount() + req.getChangeAmount();
    } else {
      newWalletAmount = member.getWalletAmount() - req.getChangeAmount();
    }

    // 更新member的walletAmount
    member.setWalletAmount(newWalletAmount);
    member.setWalletAmount(newWalletAmount);
    memberRepository.save(member);

    // 創建新的 WalletHistoryEntity 實體
    WalletHistoryEntity walletHistory = new WalletHistoryEntity();
    walletHistory.setMember(member); // 設置會員ID
    walletHistory.setChangeAmount(req.getChangeAmount());
    walletHistory.setChangeTime(new Date());
    walletHistory.setChangeType(req.getWalletCategory());

    // 保存 WalletHistoryEntity 實體
    walletHistoryRepository.save(walletHistory);

    // 回傳處理结果
    WalletCreateResDTO res = new WalletCreateResDTO();
    res.setChangeAmount(req.getChangeAmount());
    res.setChangeType(req.getWalletCategory().getMessage());
    res.setChangeTime(new Date().toString());
    res.setStatus(WalletCreateResDTO.Status.CREATE_SUCCESS.getMessage());
    return res;
  }

  @Override
  public WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId) {
    return walletHistoryDAO.getWalletHistoryById(walletHistoryId);
  }

  @Override
  public List<WalletHistoryEntity> getWallets(WalletQueryParams walletQueryParams) {
    return walletHistoryDAO.getWallets(walletQueryParams);
  }

  //  @Override
  //  public Integer createWalletHistory(WalletReqDTO walletReqDTO) {
  //    WalletCategory category = walletReqDTO.getChangeType();
  //    return handleWalletOperation(walletReqDTO, category);
  //  }
  //
  //  private Integer handleWalletOperation(WalletReqDTO walletReqDTO, WalletCategory category) {
  //    switch (category) {
  //      case PAYMENT:
  //      case TOP_UP:
  //      case DEPOSIT:
  //      case FEE:
  //      case WITHDRAW:
  //      case MARKET:
  //        return walletHistoryDAO.createWalletHistory(walletReqDTO);
  //      default:
  //        throw new IllegalArgumentException("Unsupported wallet category: " + category);
  //    }
  //  }

  //    @Override
  //    public Integer payment(PaymentAmountReqDTO paymentAmountReqDTO) {
  //        WalletReqDTO walletReqDTO = new WalletReqDTO();
  //        walletReqDTO.setMemberID(paymentAmountReqDTO.getMemberID());
  //        walletReqDTO.setChangeAmount(paymentAmountReqDTO.getPaymentAmount());
  //        walletReqDTO.setChangeType(WalletCategory.PAYMENT);
  //        return walletHistoryDAO.createWalletHistory(walletReqDTO);
  //    }
  //
  //    @Override
  //    public Integer topUp(TopUpAmountReqDTO topUpAmountReqDTO) {
  //        WalletReqDTO walletReqDTO = new WalletReqDTO();
  //        walletReqDTO.setMemberID(topUpAmountReqDTO.getMemberID());
  //        walletReqDTO.setChangeAmount(topUpAmountReqDTO.getTopUpAmount());
  //        walletReqDTO.setChangeType(WalletCategory.TOP_UP);
  //        return walletHistoryDAO.createWalletHistory(walletReqDTO);
  //    }
  //
  //    @Override
  //    public Integer deposit(DepositAmountReqDTO depositAmountReqDTO) {
  //        WalletReqDTO walletReqDTO = new WalletReqDTO();
  //        walletReqDTO.setMemberID(depositAmountReqDTO.getMemberID());
  //        walletReqDTO.setChangeAmount(depositAmountReqDTO.getDepositAmount());
  //        walletReqDTO.setChangeType(WalletCategory.DEPOSIT);
  //        return walletHistoryDAO.createWalletHistory(walletReqDTO);
  //    }
  //
  //    @Override
  //    public Integer fee(FeeAmountReqDTO feeAmountReqDTO) {
  //        WalletReqDTO walletReqDTO = new WalletReqDTO();
  //        walletReqDTO.setMemberID(feeAmountReqDTO.getMemberID());
  //        walletReqDTO.setChangeAmount(feeAmountReqDTO.getFeeAmount());
  //        walletReqDTO.setChangeType(WalletCategory.FEE);
  //        return walletHistoryDAO.createWalletHistory(walletReqDTO);
  //    }
  //
  //    @Override
  //    public Integer withdraw(WithdrawAmountReqDTO withdrawAmountReqDTO) {
  //        WalletReqDTO walletReqDTO = new WalletReqDTO();
  //        walletReqDTO.setMemberID(withdrawAmountReqDTO.getMemberID());
  //        walletReqDTO.setChangeAmount(withdrawAmountReqDTO.getWithdrawAmount());
  //        walletReqDTO.setChangeType(WalletCategory.WITHDRAW);
  //        return walletHistoryDAO.createWalletHistory(walletReqDTO);
  //    }
  //
  //    @Override
  //    public Integer market(MarketAmountReqDTO marketAmountReqDTO) {
  //        WalletReqDTO walletReqDTO = new WalletReqDTO();
  //        walletReqDTO.setMemberID(marketAmountReqDTO.getMemberID());
  //        walletReqDTO.setChangeAmount(marketAmountReqDTO.getMarketAmount());
  //        walletReqDTO.setChangeType(WalletCategory.MARKET);
  //        return walletHistoryDAO.createWalletHistory(walletReqDTO);
  //    }
}
