package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.enums.WalletCategory;
import com.tibame.group1.db.dao.WalletHistoryDAO;
import com.tibame.group1.db.dto.WalletQueryParams;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.db.entity.WalletRequestEntity;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.db.repository.WalletHistoryRepository;
import com.tibame.group1.db.repository.WalletRequestRepository;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.WalletHistoryService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class WalletHistoryServiceImpl implements WalletHistoryService {

  @Autowired private WalletHistoryDAO walletHistoryDAO;

  @Autowired private WalletHistoryRepository walletHistoryRepository;

  @Autowired private MemberRepository memberRepository;
  @Autowired private WalletRequestRepository walletRequestRepository;

  @Override
  public WalletCreateResDTO walletHistoryCreate(
      WalletCreateReqDTO req, LoginSourceDTO loginSource) {
    WalletCreateResDTO res = new WalletCreateResDTO();

    // 根據 LoginSourceDTO 中的 memberId 獲取會員資料
    Integer memberId = loginSource.getMemberId();
    MemberEntity member = memberRepository.findById(memberId).orElse(null);

    if (null == member) {
      res.setStatus(WalletCreateResDTO.Status.DO_NOT_EXIST_ACCOUNT.getMessage());
      return res;
    }

    // 計算新的 walletAmount
    int newWalletAmount;
    String categoryCode = req.getWalletCategory();
    if (categoryCode.equals("0")
        || categoryCode.equals("2")
        || categoryCode.equals("5")
        || categoryCode.equals("6")) { // 扣錢的種類
      if (req.getChangeAmount() > member.getWalletAmount()) {
        res.setStatus(WalletCreateResDTO.Status.INSUFFICIENT_BALANCE.getCode());
        return res;
      }
      newWalletAmount = member.getWalletAmount() - req.getChangeAmount();
      member.setWalletWithdrawAmount(req.getChangeAmount());
    } else { // 加錢的種類
      newWalletAmount = member.getWalletAmount() + req.getChangeAmount();
    }

    // 更新member的walletAmount
    member.setWalletAmount(newWalletAmount);
    memberRepository.save(member);

    // 創建新的 WalletHistoryEntity 實體
    WalletHistoryEntity walletHistory = new WalletHistoryEntity();
    walletHistory.setMember(member); // 設置會員ID
    walletHistory.setChangeAmount(req.getChangeAmount());
    walletHistory.setChangeTime(new Date());
    walletHistory.setChangeType(WalletCategory.fromCode(Integer.parseInt(req.getWalletCategory())));

    // 保存 WalletHistoryEntity 實體
    walletHistoryRepository.save(walletHistory);

    // 回傳處理结果
    res.setChangeAmount(req.getChangeAmount());
    res.setChangeType(req.getWalletCategory());
    res.setChangeTime(new Date().toString());
    res.setStatus(WalletCreateResDTO.Status.CREATE_SUCCESS.getCode());
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

  @Override
  public WalletWithdrawResDTO walletWithdrawCreate(
      WalletWithdrawReqDTO req, LoginSourceDTO loginSource) {
    WalletWithdrawResDTO res = new WalletWithdrawResDTO();

    Integer memberId = loginSource.getMemberId();
    MemberEntity member = memberRepository.findById(memberId).orElse(null);

    if (member == null) {
      res.setStatus(WalletWithdrawResDTO.Status.DO_NOT_EXIST_ACCOUNT.getMessage());
      return res;
    }

    if (req.getChangeAmount() > member.getWalletAmount()) {
      res.setStatus(WalletWithdrawResDTO.Status.INSUFFICIENT_BALANCE.getCode());
      return res;
    }

    WalletRequestEntity walletRequest = new WalletRequestEntity();
    walletRequest.setMemberId(member);
    walletRequest.setStatus("PENDING");
    walletRequest.setAccount(req.getAccount());
    walletRequest.setRequestDate(new Date());
    walletRequestRepository.save(walletRequest);

    res.setStatus(WalletWithdrawResDTO.Status.REQUEST_SUBMITTED.getCode());
    return res;
  }
}
