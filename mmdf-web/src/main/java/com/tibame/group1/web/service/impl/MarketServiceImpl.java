package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.enums.WalletCategory;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.utils.ConvertUtils;
import com.tibame.group1.common.utils.DateUtils;
import com.tibame.group1.db.entity.*;
import com.tibame.group1.db.repository.MarketRegistrationRepository;
import com.tibame.group1.db.repository.MarketRepository;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.db.repository.WalletHistoryRepository;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.MarketService;
import com.tibame.group1.web.service.NoticeService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MarketServiceImpl implements MarketService {

    @Autowired private MarketRepository marketRepository;

    @Autowired private MarketRegistrationRepository marketRegistrationRepository;

    @Autowired private MemberRepository memberRepository;

    @Autowired private NoticeService noticeService;

    @Autowired private WalletHistoryRepository walletHistoryRepository;


    //狀態為2的市集顯示在前台畫面
    @Override
    public List<MarketResDTO> getMarketByStatus() {
        List<MarketEntity> markets = marketRepository.findByMarketStatus(2);

        // 將查詢結果轉換為MarketResDTO
        List<MarketResDTO> marketList = new ArrayList<>();
        for (MarketEntity market : markets) {
            MarketResDTO resDTO = new MarketResDTO();
            resDTO.setMarketId(market.getMarketId());
            resDTO.setMarketName(market.getMarketName());
            resDTO.setMarketDescription(market.getMarketDescription());
            resDTO.setMarketLocation(market.getMarketLocation());
            resDTO.setMarketStart(DateUtils.dateToSting(market.getMarketStart()));
            resDTO.setMarketEnd(DateUtils.dateToSting(market.getMarketEnd()));
            resDTO.setMarketFee(market.getMarketFee());
            resDTO.setApplicantPopulation(market.getApplicantPopulation());
            resDTO.setApplicantLimit(market.getApplicantLimit());
            resDTO.setStartDate(DateUtils.dateToSting(market.getStartDate()));
            resDTO.setEndDate(DateUtils.dateToSting(market.getEndDate()));
            resDTO.setMarketStatus(market.getMarketStatus());
            resDTO.setMarketImage(
                    null == market.getMarketImage()
                            ? null
                            : "data:image/png;base64, "
                                    + ConvertUtils.bytesToBase64(market.getMarketImage()));
            marketList.add(resDTO);
        }
        return marketList;
    }

    //查詢市集詳情
    @Override
    public MarketResDTO marketDetail(Integer marketId) throws CheckRequestErrorException {

        MarketEntity market = marketRepository.findById(marketId).orElse(null);

        if (null == market) {
            throw new CheckRequestErrorException("查無此市集資料");
        }

        // 將查詢結果轉換為MarketResDTO
        MarketResDTO resDTO = new MarketResDTO();
        resDTO.setMarketId(market.getMarketId());
        resDTO.setMarketName(market.getMarketName());
        resDTO.setMarketDescription(market.getMarketDescription());
        resDTO.setMarketLocation(market.getMarketLocation());
        resDTO.setMarketStart(DateUtils.dateToSting(market.getMarketStart()));
        resDTO.setMarketEnd(DateUtils.dateToSting(market.getMarketEnd()));
        resDTO.setMarketFee(market.getMarketFee());
        resDTO.setApplicantPopulation(market.getApplicantPopulation());
        resDTO.setApplicantLimit(market.getApplicantLimit());
        resDTO.setStartDate(DateUtils.dateToSting(market.getStartDate()));
        resDTO.setEndDate(DateUtils.dateToSting(market.getEndDate()));
        resDTO.setMarketStatus(market.getMarketStatus());
        resDTO.setMarketImage(
                null == market.getMarketImage()
                        ? null
                        : "data:image/png;base64, "
                                + ConvertUtils.bytesToBase64(market.getMarketImage()));
        return resDTO;
    }

    // 根據會員id查詢報名紀錄
    @Override
    public List<MarketRegistrationResDTO> findAllByMemberId(
            LoginSourceDTO loginSource){
        Integer memberId = loginSource.getMemberId();
        MemberEntity member = memberRepository.findById(memberId).get();
        List<MarketRegistrationEntity> registrations =
                marketRegistrationRepository.findAllByMemberId(member);

        // 轉換成DTO對象並返回
        return registrations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // 將實體類轉換為DTO對象
    private MarketRegistrationResDTO mapToDTO(MarketRegistrationEntity entity) {
        MarketRegistrationResDTO dto = new MarketRegistrationResDTO();
        dto.setMarketId(entity.getMarketId().getMarketId());
        dto.setMarketName(entity.getMarketId().getMarketName());
        dto.setMarketLocation(entity.getMarketId().getMarketLocation());
        dto.setMarketStart(DateUtils.dateToSting(entity.getMarketId().getMarketStart()));
        dto.setMarketEnd(DateUtils.dateToSting(entity.getMarketId().getMarketEnd()));
        dto.setMarketFee(entity.getMarketId().getMarketFee());
        dto.setApplicantPopulation(entity.getMarketId().getApplicantPopulation());
        dto.setParticipateDate(DateUtils.dateToSting(entity.getParticipateDate()));
        dto.setStatus(entity.getStatus());
        return dto;
    }

    //會員報名動作
    @Transactional
    @Override
    public MemberRegistrationResDTO registerMemberToMarket(
            MarketRegistrationReqDTO marketRegistrationReq, LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 查找会员是否已经注册
        MemberEntity memberEntity = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (memberEntity == null) {
            throw new CheckRequestErrorException("查無會員資料");
        }

        // 检查市集ID是否为空
        if (marketRegistrationReq.getMarketId() == null) {
            throw new CheckRequestErrorException("市集ID不能为空");
        }

        // 检查登录会员ID是否为空
        if (loginSource.getMemberId() == null) {
            throw new CheckRequestErrorException("会员ID不能为空");
        }

        // 检查市集是否存在
        MarketEntity market =
                marketRepository
                        .findById(marketRegistrationReq.getMarketId())
                        .orElseThrow(() -> new CheckRequestErrorException("找不到对应该市集"));

        // 创建报名记录的复合主键
        MarketRegistrationEntity.MarketRegistrationId registrationId =
                new MarketRegistrationEntity.MarketRegistrationId();
        registrationId.setMarketId(marketRegistrationReq.getMarketId());
        registrationId.setMemberId(loginSource.getMemberId());

        // 创建并保存报名记录
        MarketRegistrationEntity marketRegistrationEntity = new MarketRegistrationEntity();
        marketRegistrationEntity.setId(registrationId);
        marketRegistrationEntity.setMemberId(memberEntity); // 將會員實體設置到報名記錄中
        marketRegistrationEntity.setParticipateDate(new Date());
        marketRegistrationEntity.setStatus(1);

        // 设置市集属性
        marketRegistrationEntity.setMarketId(market); // 这里设置了市集属性

        marketRegistrationRepository.save(marketRegistrationEntity);

        // 增加市集的报名人数
        int applicationPopulation =
                market.getApplicantPopulation() != null ? market.getApplicantPopulation() : 0;
        int applicantLimit = market.getApplicantLimit();
        if (applicationPopulation >= applicantLimit){
            log.warn("報名人數已達上限");
            throw new CheckRequestErrorException("報名人數，已達上限");
        } else {
            market.setApplicantPopulation(applicationPopulation + 1);
            marketRepository.save(market);

            Integer sellerWalletAmount = memberEntity.getWalletAmount();

            // 賣家錢包餘額計算
            if (sellerWalletAmount < market.getMarketFee()) {
                log.warn("賣家錢包餘額不足");
                throw new CheckRequestErrorException("錢包餘額不足，請先儲值");
            } else {
                memberEntity.setWalletAmount(sellerWalletAmount - market.getMarketFee());
                memberRepository.save(memberEntity);
            }

            // 創建賣家的 WalletHistoryEntity
            WalletHistoryEntity sellerWalletHistory = new WalletHistoryEntity();
            sellerWalletHistory.setMember(memberEntity); // 設置會員ID
            sellerWalletHistory.setChangeAmount(market.getMarketFee());
            sellerWalletHistory.setChangeTime(new Date());
            sellerWalletHistory.setChangeType(WalletCategory.MARKET);
            walletHistoryRepository.save(sellerWalletHistory);

            // 创建并返回 MemberRegistrationResDTO
            MemberRegistrationResDTO registrationResDTO = new MemberRegistrationResDTO();
            registrationResDTO.setMarketId(marketRegistrationReq.getMarketId());
            registrationResDTO.setMemberId(loginSource.getMemberId());
            noticeService.memberNoticeCreate(
                    marketRegistrationEntity.getMemberId(),
                    MemberNoticeEntity.NoticeCategory.MARKET,
                    "市集報名",
                    "恭喜市集報名成功");
            return registrationResDTO;
        }
    }

    //取消報名
    @Transactional
    @Override
    public MarketCancelResDTO cancelRegistration(MarketRegistrationReqDTO marketRegistrationReq, LoginSourceDTO loginSource)throws CheckRequestErrorException{
        // 查找会员是否已经注册
        MemberEntity memberEntity = memberRepository.findById(loginSource.getMemberId()).orElse(null);

        // 检查市集是否存在
        MarketEntity marketEntity =
                marketRepository
                        .findById(marketRegistrationReq.getMarketId())
                        .orElseThrow(() -> new CheckRequestErrorException("找不到对应该市集"));

        //查找報名紀錄
        MarketRegistrationEntity registration = marketRegistrationRepository.findByMarketIdAndMemberId(marketEntity, memberEntity);
        // 檢查報名記錄是否存在
        if (registration == null) {
            throw new CheckRequestErrorException("找不到對應的報名記錄");
        }

        // 更新報名記錄的狀態為取消（狀態碼2代表取消）
        registration.setStatus(2);
        marketRegistrationRepository.save(registration);

        // 市集報名人數減1
        if (marketEntity.getApplicantPopulation() != null) {
            int applicantPopulation = marketEntity.getApplicantPopulation();
            if (applicantPopulation > 0) {
                marketEntity.setApplicantPopulation(applicantPopulation - 1);
            }
        }
        marketRepository.save(marketEntity);

        // 賣家錢包增加金額
        Integer sellerWalletAmount = memberEntity.getWalletAmount();
        memberEntity.setWalletAmount(sellerWalletAmount + marketEntity.getMarketFee());
        memberRepository.save(memberEntity);


        // 創建賣家的 WalletHistoryEntity
        WalletHistoryEntity sellerWalletHistory = new WalletHistoryEntity();
        sellerWalletHistory.setMember(memberEntity); // 設置會員ID
        sellerWalletHistory.setChangeAmount(marketEntity.getMarketFee());
        sellerWalletHistory.setChangeTime(new Date());
        sellerWalletHistory.setChangeType(WalletCategory.REFUND);
        walletHistoryRepository.save(sellerWalletHistory);

        MarketCancelResDTO dto = new MarketCancelResDTO();
        dto.setStatus(registration.getStatus());
        noticeService.memberNoticeCreate(registration.getMemberId(), MemberNoticeEntity.NoticeCategory.MARKET, "市集取消報名","市集報名已取消完成");
        return dto;

    }

}
