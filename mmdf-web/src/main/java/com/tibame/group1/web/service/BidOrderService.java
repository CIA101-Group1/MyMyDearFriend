package com.tibame.group1.web.service;

import com.tibame.group1.common.dto.web.BidOrderPayReqDTO;
import com.tibame.group1.common.enums.BidOrderStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidEntity;
import com.tibame.group1.db.entity.BidOrderEntity;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.web.dto.LoginSourceDTO;

import java.util.List;
import java.util.Optional;

public interface BidOrderService {
    void createOrderForHighestBid(BidProductEntity product, Optional<BidEntity> highestBid);

    List<BidOrderEntity> findBidOrdersForSeller(LoginSourceDTO loginSourceDTO);

    List<BidOrderEntity> findBidOrdersForBuyer(LoginSourceDTO loginSourceDTO);

    BidOrderEntity findById(Integer orderId) throws CheckRequestErrorException;

    void payBidOrder(Integer orderId, BidOrderPayReqDTO req, LoginSourceDTO loginSource) throws CheckRequestErrorException;

    void completeBidOrder(Integer orderId, LoginSourceDTO loginSource);

    void cancelBidOrder(Integer orderId, LoginSourceDTO loginSource);

    void updateBidOrderStatus(Integer orderId, BidOrderStatus newStatus) throws CheckRequestErrorException;
}
