package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.dto.web.*;
import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.common.exception.EmailException;
import com.tibame.group1.common.listener.EmailSentListener;
import com.tibame.group1.common.utils.*;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.web.ConfigProperties;
import com.tibame.group1.web.dto.EmailVerifySourceDTO;
import com.tibame.group1.common.dto.web.LoginSourceDTO;
import com.tibame.group1.web.service.JwtService;
import com.tibame.group1.web.service.MemberService;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired private MemberRepository memberRepository;

    @Autowired private JwtService jwtService;

    @Autowired private ConfigProperties config;

    /**
     * 前台使用者註冊會員
     *
     * @param req 註冊時填入的資料
     * @return 已建立memberId的memberEntity
     */
    @Override
    public MemberCreateResDTO memberCreate(MemberCreateReqDTO req)
            throws DateException, IOException, CheckRequestErrorException {
        MemberEntity member = new MemberEntity();
        member.setMemberAccount(req.getMemberAccount());
        member.setCid(CommonUtils.encryptToMD5(req.getCid()));
        member.setName(req.getName());
        member.setPhone(req.getPhone());
        member.setEmail(req.getEmail());
        member.setBirth(DateUtils.stringToDate(req.getBirth(), DateUtils.DEFAULT_DATE_FORMAT));
        member.setTwPersonId(req.getTwPersonId());
        member.setCity(req.getCity());
        member.setAddress(req.getAddress());
        member.setIsVerified(false);
        member.setJoinTime(new Date());
        member.setWalletAmount(0);
        member.setWalletAvailableAmount(0);
        member.setWalletCid(req.getWalletCid());
        member.setWalletQuestion(req.getWalletQuestion());
        member.setWalletAnswer(req.getWalletAnswer());
        member.setSellerStatus(false);
        member.setImage(
                StringUtils.isEmpty(req.getImageBase64())
                        ? null
                        : ConvertUtils.base64ToBytes(req.getImageBase64()));
        member = memberRepository.save(member);
        MemberCreateResDTO resDTO = new MemberCreateResDTO();
        resDTO.setMemberId(member.getMemberId());
        LoginSourceDTO loginSource = new LoginSourceDTO();
        loginSource.setMemberId(member.getMemberId());
        loginSource.setName(member.getName());
        loginSource.setEmail(member.getEmail());
        loginSource.setIsVerified(member.getIsVerified());
        sendVerifyEmail(loginSource);
        resDTO.setAuthorization(jwtService.encodeLogin(loginSource));
        return resDTO;
    }

    /**
     * 前台使用者查詢自己的會員資料(會員資料管理)
     *
     * @param loginSource 登入憑證
     * @return 該筆會員資料
     */
    @Override
    public MemberDetailResDTO memberDetail(LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (null == member) {
            throw new CheckRequestErrorException("查無此會員資料");
        }
        MemberDetailResDTO resDTO = new MemberDetailResDTO();
        resDTO.setMemberId(String.valueOf(member.getMemberId()));
        resDTO.setMemberAccount(member.getMemberAccount());
        resDTO.setCid(member.getCid());
        resDTO.setName(member.getName());
        resDTO.setPhone(member.getPhone());
        resDTO.setEmail(member.getEmail());
        resDTO.setBirth(DateUtils.dateToSting(member.getBirth(), DateUtils.DEFAULT_DATE_FORMAT));
        resDTO.setTwPersonId(member.getTwPersonId());
        resDTO.setCity(member.getCity());
        resDTO.setAddress(member.getAddress());
        resDTO.setIsVerified(member.getIsVerified());
        resDTO.setVerificationSendingTime(
                null == member.getVerifySendingTime()
                        ? ""
                        : DateUtils.dateToSting(member.getVerifySendingTime()));
        resDTO.setVerifiedTime(
                null == member.getVerifiedTime()
                        ? ""
                        : DateUtils.dateToSting(member.getVerifiedTime()));
        resDTO.setJoinTime(
                null == member.getJoinTime() ? "" : DateUtils.dateToSting(member.getJoinTime()));
        resDTO.setWalletAmount(String.valueOf(member.getWalletAmount()));
        resDTO.setWalletAvailableAmount(String.valueOf(member.getWalletAvailableAmount()));
        resDTO.setWalletCid(member.getWalletCid());
        resDTO.setWalletQuestion(member.getWalletQuestion());
        resDTO.setWalletAnswer(member.getWalletAnswer());
        resDTO.setSellerStatus(member.getSellerStatus());
        resDTO.setScoreNumber(String.valueOf(member.getScoreNumber()));
        resDTO.setScoreSum(String.valueOf(member.getScoreSum()));
        resDTO.setImageBase64(
                null == member.getImage() ? null : ConvertUtils.bytesToBase64(member.getImage()));
        return resDTO;
    }

    /**
     * 前台使用者修改自己的會員資料
     *
     * @param req 欲修改的資料
     * @param loginSource 登入憑證
     */
    @Override
    public void memberEdit(MemberEditReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (null == member) {
            throw new CheckRequestErrorException("查無此會員資料");
        }
        if (null != req.getMemberAccount()) {
            member.setMemberAccount(req.getMemberAccount());
        }
        if (null != req.getName()) {
            member.setName(req.getName());
        }
        if (null != req.getPhone()) {
            member.setPhone(req.getPhone());
        }
        if (null != req.getEmail()) {
            if (!req.getEmail().equals(member.getEmail())) {
                member.setIsVerified(false);
                member.setEmail(req.getEmail());
            }
        }
        if (null != req.getCity()) {
            member.setCity(req.getCity());
        }
        if (null != req.getAddress()) {
            member.setAddress(req.getAddress());
        }
        if (null != req.getImageBase64()) {
            member.setImage(
                    StringUtils.isEmpty(req.getImageBase64())
                            ? null
                            : ConvertUtils.base64ToBytes(req.getImageBase64()));
        }
        memberRepository.save(member);
    }

    /**
     * 前台使用者登入會員
     *
     * @param req 登入時輸入帳號密碼
     * @return 登入狀態(成功則含登入憑證)
     */
    @Override
    public LoginResDTO memberLogin(LoginReqDTO req) throws IOException {
        LoginResDTO resDTO = new LoginResDTO();
        // 用輸入的帳號去資料庫撈相同的帳號的那一筆
        MemberEntity member = memberRepository.findByMemberAccount(req.getMemberAccount());
        // 如果撈不到
        if (null == member) {
            // 把response的狀態設定成"-1"，表示帳號或密碼錯誤
            resDTO.setStatus(LoginResDTO.Status.LOGIN_INFO_INCORRECT.getCode());
            return resDTO;
        }
        // 帳號對了，下一步要對輸入的密碼跟原密碼有沒有一樣
        // 記得要用 經過MD5加密的密碼 去比對
        // 如果不一樣
        if (!CommonUtils.encryptToMD5(req.getCid()).equals(member.getCid())) {
            // 把response的狀態設定成"-1"，表示帳號或密碼錯誤
            resDTO.setStatus(LoginResDTO.Status.LOGIN_INFO_INCORRECT.getCode());
            return resDTO;
        }
        // 密碼也對了，下一步要先確認此會員帳號的信箱驗證狀態
        if (member.getIsVerified()) {
            // 驗證過了，把response的狀態設定成"1"，表示正常
            resDTO.setStatus(LoginResDTO.Status.LOGIN_SUCCESS.getCode());
        } else {
            // 信箱尚未驗證，，把response的狀態設定成"0"，表示信箱尚未驗證
            resDTO.setStatus(LoginResDTO.Status.EMAIL_NOT_VERIFIED.getCode());
        }
        // 下一步要產登入驗證碼，用在後面需要登入的頁面，讓前端使用
        // 先設定loginSource裡面代的基本資訊
        LoginSourceDTO loginSource = new LoginSourceDTO();
        loginSource.setMemberId(member.getMemberId());
        loginSource.setName(member.getName());
        loginSource.setEmail(member.getEmail());
        loginSource.setIsVerified(member.getIsVerified());
        // 用jwt產生驗證碼，塞進去response
        resDTO.setAuthorization(jwtService.encodeLogin(loginSource));
        return resDTO;
    }

    /**
     * 前台使用者請求驗證會員信箱
     *
     * @param req 驗證碼
     * @return 驗證狀態
     */
    @Override
    public MemberVerifyResDTO memberVerify(MemberVerifyReqDTO req) {
        MemberVerifyResDTO resDTO = new MemberVerifyResDTO();
        try {
            EmailVerifySourceDTO emailVerifySource =
                    jwtService.decodeEmailVerify(req.getVerifyCode());
            MemberEntity member =
                    memberRepository.findById(emailVerifySource.getMemberId()).orElse(null);
            // 找不到使用者
            if (null == member) {
                resDTO.setStatus(MemberVerifyResDTO.Status.VERIFY_CODE_ERROR.getCode());
                return resDTO;
            }
            // 超過信箱驗證碼存活時間，驗證碼過期
            if (DateUtils.addMinute(
                            member.getVerifySendingTime(),
                            config.getEmailVerifyCodeSurvivalMinute())
                    .before(new Date())) {
                resDTO.setStatus(MemberVerifyResDTO.Status.VERIFY_CODE_EXPIRED.getCode());
                return resDTO;
            }
            // 該會員已驗證過
            if (member.getIsVerified()) {
                resDTO.setStatus(MemberVerifyResDTO.Status.VERIFY_CODE_INVALID.getCode());
                return resDTO;
            }

            resDTO.setStatus(MemberVerifyResDTO.Status.VERIFY_SUCCESS.getCode());
            member.setIsVerified(true);
            member.setVerifiedTime(new Date());
            return resDTO;
        } catch (AuthorizationException ae) {
            resDTO.setStatus(MemberVerifyResDTO.Status.VERIFY_CODE_ERROR.getCode());
            return resDTO;
        }
    }

    /**
     * 系統寄送驗證信
     *
     * @param loginSource 登入憑證
     */
    @Override
    public void sendVerifyEmail(LoginSourceDTO loginSource) throws CheckRequestErrorException {
        // 從loginSource取出memberId
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        // 取不出來則為null，表示找不到對應的會員
        if (null == member) {
            throw new CheckRequestErrorException("查無此會員資料");
        }
        // 取出後檢查其驗證狀態，若為true表示已驗證，則不寄送驗證信
        if (loginSource.getIsVerified()) {
            throw new CheckRequestErrorException("此信箱已完成驗證");
        }
        // 確定需要驗證。
        // 檢查上一次送出驗證請求的時間，若不為空表示有要求寄過驗證信
        if (null != member.getVerifySendingTime()) {
            // 若還在冷卻時間內，則不寄送驗證信
            if (DateUtils.addSecond(
                            member.getVerifySendingTime(), config.getEmailSendingCooldownSecond())
                    .after(new Date())) {
                throw new CheckRequestErrorException(
                        "冷卻時間為" + config.getEmailSendingCooldownSecond() + "秒，請稍後再試");
            }
        }
        // verifySendingTime 為空(表示為第一次送出驗證信箱請求)，或是不為空但已超過冷卻時間
        sendVerificationEmail(member);
        member.setVerifySendingTime(new Date());
        memberRepository.save(member);
    }

    /**
     * 寄送驗證信方法
     *
     * @param member 收件者memberEntity
     */
    private void sendVerificationEmail(MemberEntity member) {
        EmailVerifySourceDTO emailVerifySource = new EmailVerifySourceDTO();
        emailVerifySource.setMemberId(member.getMemberId());
        EmailUtils.init(config.getTestSendEmail(), config.getTestEmailCid())
                .setTitle("My my dear friend 會員驗證信")
                .addContent(
                        config.getWebURL()
                                + "/mmdf/web/member/verify?verifyCode="
                                + jwtService.encodeEmailVerify(emailVerifySource))
                .setIsHtml(false)
                .setSenderName("My my dear friend 管理員")
                .addSends(member.getEmail())
                .doSendOnThread(
                        new EmailSentListener() {
                            @Override
                            public void onSentSuccess() {
                                log.info("寄送驗證信成功");
                            }

                            @Override
                            public void onSentError(EmailException e) {
                                log.info("寄送驗證信失敗，錯誤資訊：" + e.getMessage());
                            }
                        });
    }
}
