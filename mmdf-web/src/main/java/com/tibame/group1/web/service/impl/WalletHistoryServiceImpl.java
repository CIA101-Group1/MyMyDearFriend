package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.dto.PagesResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeAllResDTO;
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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        walletHistory.setChangeType(
                WalletCategory.fromCode(Integer.parseInt(req.getWalletCategory())));

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
    public WalletWithdrawCreateResDTO walletWithdrawCreate(
            WalletWithdrawCreateReqDTO req, LoginSourceDTO loginSource) {
        WalletWithdrawCreateResDTO res = new WalletWithdrawCreateResDTO();

        Integer memberId = loginSource.getMemberId();
        MemberEntity member = memberRepository.findById(memberId).orElse(null);

        if (member == null) {
            res.setStatus(WalletWithdrawCreateResDTO.Status.DO_NOT_EXIST_ACCOUNT.getMessage());
            return res;
        }

        if (req.getChangeAmount() > member.getWalletAmount()) {
            res.setStatus(WalletWithdrawCreateResDTO.Status.INSUFFICIENT_BALANCE.getCode());
            return res;
        }

    WalletRequestEntity walletRequest = new WalletRequestEntity();
    walletRequest.setMember(member);
    walletRequest.setStatus("0");
    walletRequest.setAccount(req.getAccount());
    walletRequest.setRequestDate(new Date());
    walletRequestRepository.save(walletRequest);
    member.setWalletWithdrawAmount(req.getChangeAmount());
    member.setWalletAmount(member.getWalletAmount() - req.getChangeAmount());
    memberRepository.save(member);

        res.setStatus(WalletWithdrawCreateResDTO.Status.REQUEST_SUBMITTED.getCode());
        return res;
    }

    @Override
    public WalletWithdrawAllResDTO walletWithdrawAll(
            WalletWithdrawAllReqDTO req, LoginSourceDTO loginSource, Pageable pageable) {
        WalletWithdrawAllResDTO res = new WalletWithdrawAllResDTO();
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (null == member) {
            res.setStatus(MemberNoticeAllResDTO.Status.MEMBER_NOTFOUND.getCode());
            return res;
        }

        WalletWithdrawAllResDTO walletWithdrawAllResDTO = new WalletWithdrawAllResDTO();
        Page<WalletRequestEntity> pageResult;

        WalletRequestEntity filterEntity = new WalletRequestEntity();
        filterEntity.setStatus(req.getStatus());
        filterEntity.setMember(member);
        ExampleMatcher filterMatcher = ExampleMatcher.matching();
        // 將範例對象和匹配規則組合成Example對象
        Example<WalletRequestEntity> filterExample = Example.of(filterEntity, filterMatcher);
        // 調用findAll方法進行查詢和分頁
        pageResult = walletRequestRepository.findAll(filterExample, pageable);

        // 把查詢結果從Page物件拿出來塞進去List裡面
        List<WalletWithdrawCreateResDTO> walletRequestlist = new ArrayList<>();
        for (WalletRequestEntity walletRequestEntity : pageResult.getContent()) {
            WalletWithdrawCreateResDTO walletWithdrawCreateResDTO =
                    new WalletWithdrawCreateResDTO();
            walletWithdrawCreateResDTO.setStatus(walletRequestEntity.getStatus());
            walletWithdrawCreateResDTO.setRequestDate(
                    String.valueOf(walletRequestEntity.getRequestDate()));
            walletWithdrawCreateResDTO.setDoneDate(
                    String.valueOf(walletRequestEntity.getDoneDate()));
            walletWithdrawCreateResDTO.setWithdrawAmount(
                    String.valueOf(walletRequestEntity.getMember().getWalletWithdrawAmount()));
            walletRequestlist.add(walletWithdrawCreateResDTO);
        }
        PagesResDTO pagesResDTO = new PagesResDTO();
        pagesResDTO.setTotalPages(pageResult.getTotalPages());
        pagesResDTO.setTotalCount((int) pageResult.getTotalElements());
        walletWithdrawAllResDTO.setWalletWithdrawList(walletRequestlist);
        walletWithdrawAllResDTO.setPages(pagesResDTO);
        walletWithdrawAllResDTO.setStatus(WalletWithdrawAllResDTO.Status.QUERY_SUCCESS.getCode());

        return walletWithdrawAllResDTO;
    }
}
