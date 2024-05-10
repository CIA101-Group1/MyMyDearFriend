package com.tibame.group1.web.service;

import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.web.dto.EmailVerifySourceDTO;
import com.tibame.group1.web.dto.LoginSourceDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.stereotype.Service;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "CJ829JEWALKFYDS8SFDHS8VO8YFOifhhfjsdkfjhkxvjklhzudeush";

    private SecretKeySpec createSecretKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
    }

    /**
     * 產生登入驗證碼
     *
     * @param loginSource 登入資訊
     * @return 登入驗證碼
     */
    public String encodeLogin(LoginSourceDTO loginSource) {
        return Jwts.builder()
                .claim("memberId", loginSource.getMemberId())
                .claim("name", loginSource.getName())
                .claim("email", loginSource.getEmail())
                .claim("isVerified", loginSource.getIsVerified())
                .subject("登入憑證")
                .issuedAt(new Date())
                .signWith(createSecretKey())
                .compact();
    }

    /**
     * 拆解登入驗證碼
     *
     * @param authorization 登入驗證碼
     * @return 登入資訊
     */
    public LoginSourceDTO decodeLogin(String authorization) throws AuthorizationException {
        try {
            SecretKeySpec secretKey = createSecretKey();
            Claims claims =
                    Jwts.parser()
                            .decryptWith(secretKey)
                            .verifyWith(secretKey)
                            .build()
                            .parseSignedClaims(authorization)
                            .getPayload();
            LoginSourceDTO loginSource = new LoginSourceDTO();
            loginSource.setMemberId(claims.get("memberId", Integer.class));
            loginSource.setName(claims.get("name", String.class));
            loginSource.setEmail(claims.get("email", String.class));
            loginSource.setIsVerified(claims.get("isVerified", Boolean.class));
            return loginSource;
        } catch (Exception e) {
            throw new AuthorizationException("登入驗證碼檢驗失敗");
        }
    }

    /**
     * 產生信箱驗證碼
     *
     * @param emailVerifySource 驗證碼資訊
     * @return 信箱驗證碼
     */
    public String encodeEmailVerify(EmailVerifySourceDTO emailVerifySource) {
        return Jwts.builder()
                .claim("memberId", emailVerifySource.getMemberId())
                .subject("信箱驗證")
                .issuedAt(new Date())
                .signWith(createSecretKey())
                .compact();
    }

    /**
     * 拆解信箱驗證碼
     *
     * @param verifyCode 信箱驗證碼
     * @return 驗證碼資訊
     */
    public EmailVerifySourceDTO decodeEmailVerify(String verifyCode) throws AuthorizationException {
        try {
            SecretKeySpec secretKey = createSecretKey();
            Claims claims =
                    Jwts.parser()
                            .decryptWith(secretKey)
                            .verifyWith(secretKey)
                            .build()
                            .parseSignedClaims(verifyCode)
                            .getPayload();
            EmailVerifySourceDTO emailVerifySource = new EmailVerifySourceDTO();
            emailVerifySource.setMemberId(claims.get("memberId", Integer.class));
            return emailVerifySource;
        } catch (Exception e) {
            throw new AuthorizationException("信箱驗證碼檢驗失敗");
        }
    }
}
