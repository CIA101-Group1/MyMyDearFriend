package com.tibame.group1.web.schedule;

import com.tibame.group1.web.service.BidProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Slf4j
@EnableScheduling
@Configuration
public class ScheduleDemo {

    @Autowired BidProductService bidProductService;

    // 每隔一秒鐘執行一次
    // @Scheduled(fixedRate = 1000)
    // public void runScheduleFixedRate() {
    //     log.info("runScheduleFixedRate: current DateTime, {}", LocalDateTime.now());
    //     // 把競標商品狀態從"審核通過" 1 改成 "進行中" 3
    // }
    //
    // // 每個小時執行一次
    // @Scheduled(cron = "0 0 */1 * * ?")
    // public void runScheduleCron() {
    //     log.info("runScheduleCron: current DateTime, {}", LocalDateTime.now());
    // }
}
