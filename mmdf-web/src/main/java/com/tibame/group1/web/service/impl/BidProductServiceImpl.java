package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.dto.web.BidProductAddReqDTO;
import com.tibame.group1.common.enums.BidProductStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidEntity;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.db.entity.BidProductImageEntity;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.MemberNoticeEntity;
import com.tibame.group1.db.repository.BidProductImageRepository;
import com.tibame.group1.db.repository.BidProductRepository;
import com.tibame.group1.db.repository.BidRepository;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.BidOrderService;
import com.tibame.group1.web.service.BidProductService;

import com.tibame.group1.web.service.MemberNoticeService;
import com.tibame.group1.web.service.NoticeService;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class BidProductServiceImpl implements BidProductService {

    @Autowired private BidProductRepository bidProductRepository;
    @Autowired private BidProductImageRepository bidProductImageRepository;
    @Autowired private BidRepository bidRepository;
    @Autowired private BidOrderService bidOrderService;
    @Autowired private NoticeService noticeService;
    @Autowired private MemberRepository memberRepository;

    @Override
    public List<BidProductEntity> findAll() {
        return bidProductRepository.findAll();
    }

    @Override
    public BidProductEntity findById(Integer productId) {
        Optional<BidProductEntity> optional = bidProductRepository.findById(productId);
        return optional.orElse(null);
    }

    @Override
    public void add(BidProductAddReqDTO req, LoginSourceDTO loginSource) throws IOException {
        BidProductEntity bidProduct = new BidProductEntity();
        bidProduct.setSellerId(loginSource.getMemberId());
        bidProduct.setCategoryId(req.getCategoryId());
        bidProduct.setConditionId(req.getConditionId());
        bidProduct.setName(req.getName());
        bidProduct.setDescription(req.getDescription());
        bidProduct.setStartPrice(req.getStartPrice());
        bidProduct.setDuration(req.getDuration());
        bidProduct.setStatus(BidProductStatus.PENDING);
        bidProduct.setLastModified(Timestamp.from(Instant.now()));

        BidProductEntity newProduct = bidProductRepository.save(bidProduct);

        int position = 1;

        for (MultipartFile image : req.getImages()) {
            BidProductImageEntity bidProductImage = new BidProductImageEntity();
            bidProductImage.setProductId(newProduct.getProductId());
            bidProductImage.setImage(image.getBytes());
            bidProductImage.setPosition(position++);
            bidProductImageRepository.save(bidProductImage);
        }

        Optional<MemberEntity> m = memberRepository.findById(loginSource.getMemberId());
        noticeService.memberNoticeCreate(m.get(), MemberNoticeEntity.NoticeCategory.BID_PRODUCT, "競標商品新增成功", "您的競標商品：" + newProduct.getName() + " 已經新增成功");

    }

    @Override
    public void update(Integer productId, BidProductAddReqDTO req, LoginSourceDTO loginSource)
            throws IOException, CheckRequestErrorException {
        BidProductEntity bidProduct =
                bidProductRepository
                        .findById(productId)
                        .orElseThrow(() -> new CheckRequestErrorException("查無此商品資料"));

        bidProduct.setCategoryId(req.getCategoryId());
        bidProduct.setConditionId(req.getConditionId());
        bidProduct.setName(req.getName());
        bidProduct.setDescription(req.getDescription());
        bidProduct.setStartPrice(req.getStartPrice());
        bidProduct.setDuration(req.getDuration());
        bidProduct.setStatus(BidProductStatus.END);
        bidProduct.setLastModified(Timestamp.from(Instant.now()));
        Integer productId2 = bidProductRepository.save(bidProduct).getProductId();

        bidProductImageRepository.deleteByProductId(productId);

        int position = 1;

        for (MultipartFile image : req.getImages()) {
            BidProductImageEntity bidProductImage = new BidProductImageEntity();
            bidProductImage.setProductId(productId2);
            bidProductImage.setImage(image.getBytes());
            bidProductImage.setPosition(position++);
            bidProductImageRepository.save(bidProductImage);
        }
    }

    @Override
    public List<BidProductEntity> findAllForSeller(LoginSourceDTO loginSource) {
        return bidProductRepository.findAllBySellerId(loginSource.getMemberId());
    }

    @Override
    public List<BidProductEntity> findByCompositeQuery(
            Integer categoryId, Integer conditionId, String name, List<Integer> status) {
        return bidProductRepository.findByCompositeQuery(categoryId, conditionId, name, status);
    }

    @Override
    public List<BidProductEntity> findByStatus(Integer status) {
        return bidProductRepository.findAllByStatus(status);
    }

    @Override
    public void updateExpiredBidProducts() {
        Timestamp currentTime = Timestamp.from(Instant.now());
        // 取得所有已過期且狀態為開始的競標商品
        List<BidProductEntity> expiredProducts = bidProductRepository.findByEndTimeBeforeAndStatus(currentTime, BidProductStatus.START.getValue());

        for (BidProductEntity product : expiredProducts) {
            product.setStatus(BidProductStatus.END);
            bidProductRepository.save(product);

            // 找到最高出價
            Optional<BidEntity> highestBid = bidRepository.findTopByProductIdOrderByAmountDesc(product.getProductId());

            if (highestBid.isPresent()) {
                // 為最高出價者創建訂單
                bidOrderService.createOrderForHighestBid(product, highestBid);
                // 發送通知
                noticeService.memberNoticeCreate(
                        memberRepository.findById(highestBid.get().getMemberId()).get(),
                        MemberNoticeEntity.NoticeCategory.BID_PRODUCT,
                        "競標訂單新增成功",
                        "您在商品【"+ product.getName() + "】的競標中出價最高，訂單已成功建立。請前往付款",
                        false);
            }
        }
    }

    @Override
    public void closeBidEarly(Integer productId) throws CheckRequestErrorException {
        // 獲取當前時間
        Timestamp currentTime = Timestamp.from(Instant.now());

        // 獲取對應的競標商品
        Optional<BidProductEntity> optionalProduct = bidProductRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            BidProductEntity product = optionalProduct.get();

            // 更新商品結束時間
            product.setEndTime(currentTime);
            bidProductRepository.save(product);

            // 排成器會自動幫我們產生訂單...
        } else {
            throw new CheckRequestErrorException("競標商品不存在");
        }
    }

    @Override
    public List<BidProductEntity> findAllBidProductForMember(LoginSourceDTO loginSource) {
        return bidProductRepository.findAllBidProductForMember(loginSource.getMemberId());
    }
}
