package com.tibame.group1.web.service;


import com.tibame.group1.common.utils.CommonUtils;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.web.ConfigProperties;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@Slf4j
public class InitTestMemberService {

    @Autowired private ConfigProperties config;
    @Autowired private MemberRepository memberRepository;

    public void init() throws IOException {
        // 如果不使用測試使用者，就不做事
        if (!config.getUseTestMember()) {
            return;
        }

        // 如果測試使用者資料已存在，就不做事
        MemberEntity member = memberRepository.findByMemberAccount(config.getMemberAccount());
        if (null != member) {
            return;
        }

        log.info("執行初始化測試使用者");

        if (memberRepository.existsByEmail(config.getEmail())) {
            throw new IOException("該email已存在，請更換測試使用者email");
        }

        // 建立測試使用者資料
        member = new MemberEntity();
        member.setMemberAccount(config.getMemberAccount());
        member.setCid(CommonUtils.encryptToMD5(config.getCid()));
        member.setName(config.getName());
        member.setPhone(config.getPhone());
        member.setEmail(config.getEmail());
        member.setBirth(new Date());
        member.setTwPersonId(config.getTwPersonId());
        member.setCity("臺北市");
        member.setAddress("臺北市_中山區_testAccount");
        member.setIsVerified(true);
        member.setJoinTime(new Date());
        member.setVerifySendingTime(new Date());
        member.setVerifiedTime(new Date());
        member.setWalletAmount(0);
        member.setWalletWithdrawAmount(0);
        member.setWalletCid(config.getWalletCid());
        member.setWalletQuestion(config.getWalletQuestion());
        member.setWalletAnswer(config.getWalletAnswer());
        member.setSellerStatus(false);
        memberRepository.save(member);
        log.info("完成初始化測試使用者");

        // 建立測試使用者資料
        member = new MemberEntity();
        member.setMemberAccount(config.getMemberAccount() + '2');
        member.setCid(CommonUtils.encryptToMD5(config.getCid()));
        member.setName(config.getName());
        member.setPhone(config.getPhone());
        member.setEmail(config.getEmail()  + '2');
        member.setBirth(new Date());
        member.setTwPersonId(config.getTwPersonId());
        member.setCity("臺北市");
        member.setAddress("臺北市_中山區_testAccount");
        member.setIsVerified(true);
        member.setJoinTime(new Date());
        member.setVerifySendingTime(new Date());
        member.setVerifiedTime(new Date());
        member.setWalletAmount(0);
        member.setWalletWithdrawAmount(0);
        member.setWalletCid(config.getWalletCid());
        member.setWalletQuestion(config.getWalletQuestion());
        member.setWalletAnswer(config.getWalletAnswer());
        member.setSellerStatus(false);
        memberRepository.save(member);
        log.info("完成初始化測試使用者2");

        // 建立測試使用者資料
        member = new MemberEntity();
        member.setMemberAccount(config.getMemberAccount()  + '3');
        member.setCid(CommonUtils.encryptToMD5(config.getCid()));
        member.setName(config.getName());
        member.setPhone(config.getPhone());
        member.setEmail(config.getEmail()  + '3');
        member.setBirth(new Date());
        member.setTwPersonId(config.getTwPersonId());
        member.setCity("臺北市");
        member.setAddress("臺北市_中山區_testAccount");
        member.setIsVerified(true);
        member.setJoinTime(new Date());
        member.setVerifySendingTime(new Date());
        member.setVerifiedTime(new Date());
        member.setWalletAmount(0);
        member.setWalletWithdrawAmount(0);
        member.setWalletCid(config.getWalletCid());
        member.setWalletQuestion(config.getWalletQuestion());
        member.setWalletAnswer(config.getWalletAnswer());
        member.setSellerStatus(false);
        memberRepository.save(member);
        log.info("完成初始化測試使用者3");
    }
}
