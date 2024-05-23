package com.tibame.group1.web.schedule;


import com.tibame.group1.web.service.BidProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BidProductScheduler {

    @Autowired
    private BidProductService bidProductService;

    @Scheduled(fixedRate = 5000)
    public void updateExpiredBidProductsStatus() {
        bidProductService.updateExpiredBidProducts();
    }

}