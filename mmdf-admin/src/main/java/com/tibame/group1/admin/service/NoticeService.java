package com.tibame.group1.admin.service;

import com.tibame.group1.admin.ConfigProperties;
import com.tibame.group1.common.exception.EmailException;
import com.tibame.group1.common.listener.EmailSentListener;
import com.tibame.group1.common.utils.EmailUtils;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.MemberNoticeEntity;
import com.tibame.group1.db.repository.MemberNoticeRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class NoticeService {
    @Autowired private MemberNoticeRepository memberNoticeRepository;

    @Autowired private ConfigProperties config;

    public void memberNoticeCreate(
            MemberEntity member,
            MemberNoticeEntity.NoticeCategory noticeCategory,
            String noticeTitle,
            String noticeContent,
            boolean sendEmail) {
        MemberNoticeEntity memberNotice = new MemberNoticeEntity();
        memberNotice.setMember(member);
        memberNotice.setNoticeTitle(noticeTitle);
        memberNotice.setNoticeContent(noticeContent);
        memberNotice.setNoticeCategory(noticeCategory);
        memberNotice.setSendingTime(new Date());
        memberNoticeRepository.save(memberNotice);
        if (sendEmail) {
            sendNoticeEmail(member, noticeTitle, noticeContent);
        }
    }

    public void memberNoticeCreate(
            MemberEntity member,
            MemberNoticeEntity.NoticeCategory noticeCategory,
            String noticeTitle,
            String noticeContent) {
        memberNoticeCreate(member, noticeCategory, noticeTitle, noticeContent, false);
    }

    private void sendNoticeEmail(MemberEntity member, String noticeTitle, String noticeContent) {
        EmailUtils.init(config.getTestSendEmail(), config.getTestEmailCid())
                .setTitle(noticeTitle)
                .addContent(noticeContent)
                .setIsHtml(false)
                .setSenderName("My my dear friend 管理員")
                .addSends(member.getEmail())
                .doSendOnThread(
                        new EmailSentListener() {
                            @Override
                            public void onSentSuccess() {
                                log.info("寄送通知信成功");
                            }

                            @Override
                            public void onSentError(EmailException e) {
                                log.info("寄送通知信失敗，錯誤資訊：" + e.getMessage());
                            }
                        });
    }
}
