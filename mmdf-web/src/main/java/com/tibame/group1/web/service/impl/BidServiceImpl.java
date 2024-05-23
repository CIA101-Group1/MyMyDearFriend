package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.dto.web.BidAddReqDTO;
import com.tibame.group1.common.enums.BidProductStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidEntity;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.repository.BidProductRepository;
import com.tibame.group1.db.repository.BidRepository;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.BidService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BidServiceImpl implements BidService {

    @Autowired private BidProductRepository bidProductRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private BidRepository bidRepository;

    @Override
    public void addBid(BidAddReqDTO req, LoginSourceDTO loginSource) throws CheckRequestErrorException {
        // 確認商品存在且有效
        BidProductEntity product =
                bidProductRepository
                        .findById(req.getProductId())
                        .orElseThrow(() -> new CheckRequestErrorException("商品編號：查無此商品資料"));
        if (!product.getStatus().equals(BidProductStatus.START.getDescription())) {
            throw new CheckRequestErrorException("該商品目前不開放競標");
        }

        // 確認會員存在
        MemberEntity member =
                memberRepository
                        .findById(loginSource.getMemberId())
                        .orElseThrow(() -> new CheckRequestErrorException("查無此會員"));

        if (Objects.equals(member.getMemberId(), product.getSellerId())) {
            throw new CheckRequestErrorException("賣家不能競標自己的商品");
        }

        // 確認新出價高於當前最高出價
        Optional<BidEntity> highestBid = bidRepository.findTopByProductIdOrderByAmountDesc(req.getProductId());
        if (highestBid.isPresent() && req.getAmount() <= highestBid.get().getAmount()) {
            throw new CheckRequestErrorException("出價金額須高於現在最高的出價");
        }
        if (highestBid.isEmpty() && req.getAmount() <= product.getStartPrice()) {
            throw new CheckRequestErrorException("出價金額須高於起標價");
        }
        // 創建新的出價記錄
        BidEntity bid = new BidEntity();
        bid.setProductId(req.getProductId());
        bid.setMemberId(loginSource.getMemberId());
        bid.setAmount(req.getAmount());
        bid.setBidTime(Timestamp.from(Instant.now()));

        // 保存出價記錄
        bidRepository.save(bid);
    }

    @Override
    public List<BidEntity> findBidsByProductId(Integer productId) {
        return bidRepository.findByProductIdOrderByIdDesc(productId);
    }

    @Override
    public Integer findCurrentPriceForProduct(Integer productId) {
        Optional<BidEntity> highestBid = bidRepository.findTopByProductIdOrderByAmountDesc(productId);
        if (highestBid.isPresent()) {
            return highestBid.get().getAmount();
        } else {
            return bidProductRepository.findById(productId).get().getStartPrice();
        }
    }
}
