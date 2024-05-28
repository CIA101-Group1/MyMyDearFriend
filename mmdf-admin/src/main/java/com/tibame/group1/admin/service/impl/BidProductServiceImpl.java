package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.service.BidProductService;
import com.tibame.group1.admin.service.NoticeService;
import com.tibame.group1.common.enums.BidProductStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.db.entity.MemberNoticeEntity;
import com.tibame.group1.db.repository.BidProductImageRepository;
import com.tibame.group1.db.repository.BidProductRepository;

import com.tibame.group1.db.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BidProductServiceImpl implements BidProductService {

    @Autowired private BidProductRepository bidProductRepository;
    @Autowired private BidProductImageRepository bidProductImageRepository;
    @Autowired private NoticeService noticeService;
    @Autowired private MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public List<BidProductEntity> findAll() {
        return bidProductRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public BidProductEntity findById(Integer productId) {
        Optional<BidProductEntity> optional = bidProductRepository.findById(productId);
        return optional.orElse(null);
    }

    @Override
    public void updateBidProductReviewStatus(Integer productId, Integer newStatus)
            throws CheckRequestErrorException {
        BidProductEntity product =
                bidProductRepository
                        .findById(productId)
                        .orElseThrow(() -> new CheckRequestErrorException("查無此商品資料"));

        BidProductStatus status = BidProductStatus.fromValue(newStatus);
        product.setStatus(status);
        if (newStatus == 1) {
            product.setStatus(BidProductStatus.START);
            Instant now = Instant.now();
            product.setStartTime(Timestamp.from(now));
            ChronoUnit chronoUnit = ChronoUnit.DAYS;
            product.setEndTime(Timestamp.from(now.plus(product.getDuration(), chronoUnit)));
            product.setLastModified(Timestamp.from(now));
            bidProductRepository.save(product);
            // 發送通知
            noticeService.memberNoticeCreate(
                    memberRepository.findById(product.getSellerId()).get(),
                    MemberNoticeEntity.NoticeCategory.GENERAL_PRODUCT,
                    "競標商品審核已通過",
                    "您的競標商品：" + product.getName() + "審核已通過",
                    true);
        } else if (newStatus == -1) {
            product.setStatus(BidProductStatus.REJECT);
            product.setLastModified(Timestamp.from(Instant.now()));
            bidProductRepository.save(product);
            // 發送通知
            noticeService.memberNoticeCreate(
                    memberRepository.findById(product.getSellerId()).get(),
                    MemberNoticeEntity.NoticeCategory.GENERAL_PRODUCT,
                    "競標商品審核不通過",
                    "您的競標商品：" + product.getName() + "審核不通過",
                    true);
        }
    }

    @Override
    public List<BidProductEntity> findByCompositeQuery(
            Integer categoryId, String name, List<Integer> status) {
        return bidProductRepository.findByCompositeQuery(categoryId, name, status);
    }
}
