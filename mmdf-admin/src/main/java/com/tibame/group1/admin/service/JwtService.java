package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.AdminLoginSourceDTO;
import com.tibame.group1.common.exception.AuthorizationException;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY =
            "3Kl9hDf7Rg2Jn8Oq5Ws1Xz4Vu0yPc6Bm5Tj1Gf9Nv3Hr7Yx4Ew2Qo8";


    private SecretKeySpec createSecretKey(){return new SecretKeySpec(SECRET_KEY.getBytes(),"HmacSHA256");}

    /**
     * 產生登入驗證碼
     */
    public String encodeLogin(AdminLoginSourceDTO adminLoginSource){
        return Jwts.builder()
                .claim("employeeId",adminLoginSource.getEmployeeId())
                .claim("employeeName",adminLoginSource.getEmployeeName())
                .subject("登入憑證")
                .issuedAt(new Date())
                .signWith(createSecretKey())
                .compact();
    }

    /**
     * 拆解登入驗證碼
     */
    public AdminLoginSourceDTO decodeLogin(String authorization) throws AuthorizationException{
        try{
            SecretKeySpec secretKey = createSecretKey();
            Claims claims =
                    Jwts.parser()
                            .decryptWith(secretKey)
                            .verifyWith(secretKey)
                            .build()
                            .parseSignedClaims(authorization)
                            .getPayload();
            AdminLoginSourceDTO adminLoginSource = new AdminLoginSourceDTO();
            adminLoginSource.setEmployeeId(claims.get("employeeId",Integer.class));
            adminLoginSource.setEmployeeName(claims.get("employeeName",String.class));
            return adminLoginSource;
        }catch (Exception e){
            throw new AuthorizationException("登入驗證碼檢驗失敗");
        }
    }
}