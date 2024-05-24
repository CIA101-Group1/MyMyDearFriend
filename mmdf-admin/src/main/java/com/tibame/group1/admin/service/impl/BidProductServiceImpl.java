package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.service.BidProductService;
import com.tibame.group1.common.enums.BidProductStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.db.repository.BidProductImageRepository;
import com.tibame.group1.db.repository.BidProductRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

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

    // @Value("${bid.product.duration.unit}")
    // private String durationUnit;

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
        // find existing product
        // update it status
        // save updated product entity
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
            ChronoUnit chronoUnit = ChronoUnit.HOURS;
            product.setEndTime(Timestamp.from(now.plus(product.getDuration(), chronoUnit)));
            product.setLastModified(Timestamp.from(now));
        } else if (newStatus == -1) {
            product.setStatus(BidProductStatus.REJECT);
            product.setLastModified(Timestamp.from(Instant.now()));
        }
        bidProductRepository.save(product);
    }

    @Override
    public List<BidProductEntity> findByCompositeQuery(
            Integer categoryId, String name, List<Integer> status) {
        return bidProductRepository.findByCompositeQuery(categoryId, name, status);
    }
}
