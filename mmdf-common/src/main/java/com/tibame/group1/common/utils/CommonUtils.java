package com.tibame.group1.common.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 常用共用方法
 *
 * @author peihui
 */
public class CommonUtils {

    /**
     * MD5加密
     *
     * @param originalString 須加密字串
     * @return 加密後字串
     * @throws IOException 加密錯誤
     */
    public static String encryptToMD5(String originalString) throws IOException {
        try {
            // 創建 MessageDigest 實例，並指定算法為 MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 將原始字串轉換為 byte 陣列
            byte[] originalBytes = originalString.getBytes();
            // 使用 MessageDigest 更新原始字串的 byte 陣列
            md.update(originalBytes);
            // 執行加密，獲取加密後的 byte 陣列
            byte[] encryptedBytes = md.digest();
            // 將加密後的 byte 陣列轉換為十六進制字串
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : encryptedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            // 返回加密後的字串
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("MD5加密失敗");
        }
    }
}
