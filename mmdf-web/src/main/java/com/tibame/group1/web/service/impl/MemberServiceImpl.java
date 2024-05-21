package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.dto.web.*;
import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.common.exception.EmailException;
import com.tibame.group1.common.listener.EmailSentListener;
import com.tibame.group1.common.utils.*;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.web.ConfigProperties;
import com.tibame.group1.web.dto.CidResetVerifySourceDTO;
import com.tibame.group1.web.dto.EmailVerifySourceDTO;
import com.tibame.group1.web.dto.LoginSourceDTO;
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
            throws DateException, IOException {
        MemberCreateResDTO resDTO = new MemberCreateResDTO();
        MemberEntity member = new MemberEntity();
        if (null != memberRepository.findByMemberAccount(req.getMemberAccount())) {
            resDTO.setStatus(MemberCreateResDTO.Status.EXIST_ACCOUNT.getCode());
            return resDTO;
        }
        member.setMemberAccount(req.getMemberAccount());
        member.setCid(CommonUtils.encryptToMD5(req.getCid()));
        member.setName(req.getName());
        member.setPhone(req.getPhone());
        if ((memberRepository.existsByEmail(req.getEmail()))) {
            resDTO.setStatus(MemberCreateResDTO.Status.EXIST_EMAIL.getCode());
            return resDTO;
        }
        member.setEmail(req.getEmail());
        member.setBirth(DateUtils.stringToDate(req.getBirth(), DateUtils.DEFAULT_DATE_FORMAT));
        member.setTwPersonId(req.getTwPersonId());
        member.setCity(req.getCity());
        member.setAddress(req.getAddress());
        member.setIsVerified(false);
        member.setJoinTime(new Date());
        member.setWalletAmount(0);
        member.setWalletAvailableAmount(0);
        member.setWalletCid(CommonUtils.encryptToMD5(req.getWalletCid()));
        member.setWalletQuestion(req.getWalletQuestion());
        member.setWalletAnswer(req.getWalletAnswer());
        member.setSellerStatus(false);
        if (!StringUtils.isEmpty(req.getImageBase64())) {
            if (!FileUtils.ImageFormatChecker(req.getImageBase64().split(",")[1])) {
                resDTO.setStatus(MemberCreateResDTO.Status.IMAGE_FORMAT_ERROR.getCode());
                return resDTO;
            } else {
                member.setImage(ConvertUtils.base64ToBytes(req.getImageBase64().split(",")[1]));
            }
        }
        member = memberRepository.save(member);
        resDTO.setMemberId(String.valueOf(member.getMemberId()));
        LoginSourceDTO loginSource = new LoginSourceDTO();
        loginSource.setMemberId(member.getMemberId());
        loginSource.setName(member.getName());
        loginSource.setEmail(member.getEmail());
        loginSource.setIsVerified(member.getIsVerified());
        sendVerifyEmail(loginSource);
        resDTO.setAuthorization(jwtService.encodeLogin(loginSource));
        resDTO.setStatus(MemberCreateResDTO.Status.CREATE_SUCCESS.getCode());
        return resDTO;
    }

    /**
     * 前台使用者查詢自己的會員資料(會員資料管理)
     *
     * @param loginSource 登入憑證
     * @return 該筆會員資料
     */
    @Override
    public MemberDetailResDTO memberDetail(LoginSourceDTO loginSource) {
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        MemberDetailResDTO resDTO = new MemberDetailResDTO();
        if (null == member) {
            resDTO.setStatus(MemberDetailResDTO.Status.MEMBER_NOTFOUND.getCode());
            return resDTO;
        }
        resDTO.setMemberAccount(member.getMemberAccount());
        resDTO.setName(member.getName());
        resDTO.setPhone(member.getPhone());
        resDTO.setEmail(member.getEmail());
        resDTO.setBirth(DateUtils.dateToSting(member.getBirth(), DateUtils.DEFAULT_DATE_FORMAT));
        resDTO.setTwPersonId(member.getTwPersonId());
        resDTO.setCity(member.getCity());
        resDTO.setAddress(member.getAddress());
        resDTO.setIsVerified(member.getIsVerified());
        resDTO.setSellerStatus(member.getSellerStatus());
        resDTO.setScoreNumber(member.getScoreNumber());
        resDTO.setScoreSum(member.getScoreSum());
        resDTO.setImageBase64(
                null == member.getImage() ? null : ConvertUtils.bytesToBase64(member.getImage()));
        resDTO.setStatus(MemberDetailResDTO.Status.QUERY_SUCCESS.getCode());
        return resDTO;
    }

    /**
     * 前台使用者修改自己的會員資料
     *
     * @param req 欲修改的資料
     * @param loginSource 登入憑證
     */
    @Override
    public MemberEditResDTO memberEdit(MemberEditReqDTO req, LoginSourceDTO loginSource) {
        MemberEditResDTO resDTO = new MemberEditResDTO();
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (null == member) {
            resDTO.setStatus(MemberEditResDTO.Status.MEMBER_NOTFOUND.getCode());
            return resDTO;
        }
        if (null != req.getMemberAccount()) {
            MemberEntity member2 = memberRepository.findByMemberAccount(req.getMemberAccount());
            if (null != member2 && !member2.getMemberId().equals(member.getMemberId())) {
                resDTO.setStatus(MemberEditResDTO.Status.EXIST_ACCOUNT.getCode());
                return resDTO;
            }
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
                            : ConvertUtils.base64ToBytes(req.getImageBase64().split(",")[1]));
        } else {
            member.setImage(null);
        }
        memberRepository.save(member);
        resDTO.setStatus(MemberEditResDTO.Status.EDIT_SUCCESS.getCode());
        return resDTO;
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

            member.setIsVerified(true);
            member.setVerifiedTime(new Date());
            memberRepository.save(member);
            resDTO.setStatus(MemberVerifyResDTO.Status.VERIFY_SUCCESS.getCode());
            return resDTO;
        } catch (AuthorizationException ae) {
            resDTO.setStatus(MemberVerifyResDTO.Status.VERIFY_CODE_ERROR.getCode());
            return resDTO;
        }
    }

    /**
     * 系統寄送會員信箱驗證信
     *
     * @param loginSource 登入憑證
     * @return 寄送結果狀態
     */
    @Override
    public SendVerifyEmailResDTO sendVerifyEmail(LoginSourceDTO loginSource) {
        SendVerifyEmailResDTO resDTO = new SendVerifyEmailResDTO();
        // 從loginSource取出memberId
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        // 取不出來則為null，表示找不到對應的會員
        if (null == member) {
            resDTO.setStatus(SendVerifyEmailResDTO.Status.MEMBER_NOTFOUND.getCode());
            return resDTO;
        }
        // 取出後檢查其驗證狀態，若為true表示已驗證，則不寄送驗證信
        if (loginSource.getIsVerified()) {
            resDTO.setStatus(SendVerifyEmailResDTO.Status.ALREADY_VERIFIED.getCode());
            return resDTO;
        }
        // 確定需要驗證。
        // 檢查上一次送出驗證請求的時間，若不為空表示有要求寄過驗證信
        if (null != member.getVerifySendingTime()) {
            // 若還在冷卻時間內，則不寄送驗證信
            if (DateUtils.addSecond(
                            member.getVerifySendingTime(),
                            config.getEmailVerifySendingCooldownSecond())
                    .after(new Date())) {
                resDTO.setStatus(SendVerifyEmailResDTO.Status.COOLDOWN_TIME_ERROR.getCode());
                return resDTO;
            }
        }
        // verifySendingTime 為空(表示為第一次送出驗證信箱請求)，或是不為空但已超過冷卻時間
        sendVerificationEmail(member);
        member.setVerifySendingTime(new Date());
        memberRepository.save(member);
        resDTO.setStatus(SendVerifyEmailResDTO.Status.SEND_SUCCESS.getCode());
        return resDTO;
    }

    /**
     * 系統寄送重設密碼驗證信
     *
     * @param req 註冊信箱
     * @return 寄送狀態
     */
    @Override
    public MemberCidForgetResDTO memberCidForget(MemberCidForgetReqDTO req) {
        MemberCidForgetResDTO resDTO = new MemberCidForgetResDTO();
        // 先檢查所填email是否是某一筆會員的資料
        if (!memberRepository.existsByEmail(req.getEmail())) { // 不是會員
            resDTO.setStatus(MemberCidForgetResDTO.Status.EMAIL_NOTFOUND.getCode());
            return resDTO;
        }
        // 是會員
        MemberEntity member = memberRepository.findByEmail(req.getEmail());
        // 檢查上一次送出重設密碼請求的時間，若不為空表示有要求寄過驗證信
        if (null != member.getCidResetSendingTime()) {
            // 若還在冷卻時間內，則不寄送驗證信
            if (DateUtils.addSecond(
                            member.getCidResetSendingTime(),
                            config.getCidResetSendingCooldownSecond())
                    .after(new Date())) {
                resDTO.setStatus(MemberCidForgetResDTO.Status.COOLDOWN_TIME_ERROR.getCode());
                return resDTO;
            }
        }
        // cidResetSendingTime為空(表示為第一次送出重設密碼請求)，或是不為空但已超過冷卻時間
        sendCidResetVerify(member);
        member.setCidResetSendingTime(new Date());
        memberRepository.save(member);
        resDTO.setStatus(MemberCidForgetResDTO.Status.EMAIL_SEND_SUCCESS.getCode());
        return resDTO;
    }

    @Override
    public MemberCidResetResDTO memberCidReset(MemberCidResetReqDTO req) throws IOException {
        MemberCidResetResDTO resDTO = new MemberCidResetResDTO();
        try {
            CidResetVerifySourceDTO cidResetVerifySource =
                    jwtService.decodeCidResetVerify(req.getVerifyCode());
            MemberEntity member =
                    memberRepository.findByCidResetVerifyUUID(
                            cidResetVerifySource.getCidResetVerifyUUID());
            if (null == member) {
                resDTO.setStatus(MemberCidResetResDTO.Status.VERIFY_CODE_ERROR.getCode());
                return resDTO;
            }
            if (DateUtils.addMinute(
                            member.getCidResetSendingTime(),
                            config.getCidResetVerifyCodeSurvivalMinute())
                    .before(new Date())) {
                resDTO.setStatus(MemberCidResetResDTO.Status.VERIFY_CODE_EXPIRED.getCode());
                return resDTO;
            }
            if (!req.getNewCid().equals(req.getNewCidCheck())) {
                resDTO.setStatus(MemberCidResetResDTO.Status.CHECK_CID_DIFFERENCE.getCode());
                return resDTO;
            }
            member.setCid(CommonUtils.encryptToMD5(req.getNewCid()));
            memberRepository.save(member);
            resDTO.setStatus(MemberCidResetResDTO.Status.RESET_CID_SUCCESS.getCode());
            return resDTO;
        } catch (AuthorizationException e) {
            resDTO.setStatus(MemberCidResetResDTO.Status.VERIFY_CODE_ERROR.getCode());
            return resDTO;
        }
    }

    /**
     * 寄送會員信箱驗證信方法
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
                                + "/member/verify?verifyCode="
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

    /**
     * 寄送重設密碼驗證信方法
     *
     * @param member 收件者memberEntity
     */
    private void sendCidResetVerify(MemberEntity member) {
        CidResetVerifySourceDTO cidResetVerifySource = new CidResetVerifySourceDTO();
        cidResetVerifySource.setCidResetVerifyUUID(CommonUtils.generateUUID());
        member.setCidResetVerifyUUID(cidResetVerifySource.getCidResetVerifyUUID());
        memberRepository.save(member);
        EmailUtils.init(config.getTestSendEmail(), config.getTestEmailCid())
                .setTitle("My my dear friend 重設密碼驗證信")
                .addContent(
                        config.getWebURL()
                                + "/member/cidReset?verifyCode="
                                + jwtService.encodeCidResetVerify(cidResetVerifySource))
                .setIsHtml(false)
                .setSenderName("My my dear friend 管理員")
                .addSends(member.getEmail())
                .doSendOnThread(
                        new EmailSentListener() {
                            @Override
                            public void onSentSuccess() {
                                log.info("寄送重設密碼驗證信成功");
                            }

                            @Override
                            public void onSentError(EmailException e) {
                                log.info("寄送重設密碼驗證信失敗，錯誤資訊：" + e.getMessage());
                            }
                        });
    }
}
