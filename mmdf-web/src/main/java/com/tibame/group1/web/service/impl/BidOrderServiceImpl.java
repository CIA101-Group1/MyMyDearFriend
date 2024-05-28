package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.dto.web.BidOrderPayReqDTO;
import com.tibame.group1.common.enums.BidOrderStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidEntity;
import com.tibame.group1.db.entity.BidOrderEntity;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.repository.BidOrderRepository;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.db.dto.WalletCreateReqDTO;
import com.tibame.group1.db.dto.WalletCreateResDTO;
import com.tibame.group1.web.service.BidOrderService;
import com.tibame.group1.web.service.WalletHistoryService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class BidOrderServiceImpl implements BidOrderService {

    @Autowired BidOrderRepository bidOrderRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired WalletHistoryService walletHistoryService;

    @Override
    public void createOrderForHighestBid(BidProductEntity product, Optional<BidEntity> highestBid) {
        BidOrderEntity order = new BidOrderEntity();
        order.setProductId(product.getProductId());
        order.setBuyerId(highestBid.get().getMemberId());
        order.setSellerId(product.getSellerId());
        order.setSubtotal(highestBid.get().getAmount());
        order.setStatus(BidOrderStatus.UNPAID);
        order.setCreateTime(Timestamp.from(Instant.now()));
        order.setUpdateTime(Timestamp.from(Instant.now()));
        bidOrderRepository.save(order);
    }

    @Override
    public List<BidOrderEntity> findBidOrdersForSeller(LoginSourceDTO loginSource) {
        return bidOrderRepository.findAllBySellerId(loginSource.getMemberId());
    }

    @Override
    public List<BidOrderEntity> findBidOrdersForBuyer(LoginSourceDTO loginSource) {
        return bidOrderRepository.findAllByBuyerId(loginSource.getMemberId());
    }

    @Override
    public BidOrderEntity findById(Integer orderId) throws CheckRequestErrorException {
        BidOrderEntity order =
                bidOrderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new CheckRequestErrorException("查無此競標訂單"));
        return order;
    }

    // 更新訂單狀態：賣家出貨、買家完成訂單
    @Override
    public void updateBidOrderStatus(Integer orderId, BidOrderStatus newStatus)
            throws CheckRequestErrorException {
        BidOrderEntity order =
                bidOrderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new CheckRequestErrorException("查無此競標訂單"));

        order.setStatus(newStatus);
        order.setUpdateTime(Timestamp.from(Instant.now()));
        bidOrderRepository.save(order);
    }

    @Override
    public void payBidOrder(Integer orderId, BidOrderPayReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 檢查訂單是否存在
        BidOrderEntity order =
                bidOrderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new CheckRequestErrorException("查無此競標訂單"));
        // 檢核錢包密碼
        MemberEntity member =
                memberRepository
                        .findById(loginSource.getMemberId())
                        .orElseThrow(() -> new CheckRequestErrorException("查無此會員資料"));
        if (!req.getWalletCid().equals(member.getWalletCid())) {
            throw new CheckRequestErrorException("錢包密碼錯誤");
        }
        // 新增錢包扣款紀錄
        WalletCreateReqDTO walletCreateReqDTO = new WalletCreateReqDTO();
        walletCreateReqDTO.setWalletCategory("0");
        walletCreateReqDTO.setChangeAmount(req.getTotal());
        WalletCreateResDTO walletCreateResDTO =
                walletHistoryService.walletHistoryCreate(walletCreateReqDTO, loginSource);
        // 餘額不足
        if (Objects.equals(
                walletCreateResDTO.getStatus(),
                WalletCreateResDTO.Status.INSUFFICIENT_BALANCE.getCode())) {
            throw new CheckRequestErrorException(
                    WalletCreateResDTO.Status.INSUFFICIENT_BALANCE.getMessage());
        }
        // 更新訂單狀態
        if (Objects.equals(
                walletCreateResDTO.getStatus(),
                WalletCreateResDTO.Status.CREATE_SUCCESS.getCode())) {
            order.setStatus(BidOrderStatus.UNSHIPPED);
            order.setName(req.getName());
            order.setPhone(req.getPhone());
            order.setEmail(req.getEmail());
            order.setAddress(req.getAddress());
            order.setDiscount(req.getDiscount());
            order.setTotal(req.getTotal());
            order.setFee((int) Math.round(order.getSubtotal() * 0.03));
            order.setUpdateTime(Timestamp.from(Instant.now()));
            bidOrderRepository.save(order);
        }
    }

    @Override
    public void completeBidOrder(Integer orderId, LoginSourceDTO loginSource) {

    }

    @Override
    public void cancelBidOrder(Integer orderId, LoginSourceDTO loginSource) {

    }
}
