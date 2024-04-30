package com.tibame.group1.frontend;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("config")
@Getter
@Setter
public class ConfigProperties extends com.tibame.group1.common.ConfigProperties {

    /** 是否使用測試使用者 */
    private Boolean useTestMember;

    /** 測試使用者用登入憑證 */
    private String testMemberAuthorization;

    /** 測試使用者帳號 */
    private String memberAccount;

    /** 測試使用者密碼 */
    private String cid;

    /** 測試使用者姓名 */
    private String name;

    /** 測試使用者電話 */
    private String phone;

    /** 測試使用者email */
    private String email;

    /** 測試使用者身分證字號 */
    private String twPersonId;

    /** 測試使用者錢包密碼 */
    private String walletCid;

    /** 測試使用者錢包密碼提示問題 */
    private String walletQuestion;

    /** 測試使用者錢包密碼提示答案 */
    private String walletAnswer;

    /** 信箱驗證碼存活時間 */
    private Integer emailVerifyCodeSurvivalMinute;

    /** 測試寄驗證信用信箱帳號 */
    private String testSendEmail;

    /** 測試寄驗證信用信箱密碼 */
    private String testEmailCid;

    /** 發送驗證信間隔時間 */
    private Integer emailSendingCooldownSecond;
}
