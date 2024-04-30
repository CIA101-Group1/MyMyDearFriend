package com.tibame.group1.common.utils;

import java.util.Base64;

/**
 * 常用轉換共用方法
 *
 * @author peihui
 */
public class ConvertUtils {

    /**
     * 把 byte 陣列轉成 base64 字串
     *
     * @param bytes byte 陣列
     * @return base64 字串
     */
    public static String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 把 base64 字串轉成 byte 陣列
     *
     * @param base64 base64 字串
     * @return byte 陣列
     */
    public static byte[] base64ToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
