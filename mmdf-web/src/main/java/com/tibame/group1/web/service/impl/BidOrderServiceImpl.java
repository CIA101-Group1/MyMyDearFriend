package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.enums.BidOrderStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidEntity;
import com.tibame.group1.db.entity.BidOrderEntity;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.db.repository.BidOrderRepository;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.BidOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class BidOrderServiceImpl implements BidOrderService {

    @Autowired
    BidOrderRepository bidOrderRepository;
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
}
