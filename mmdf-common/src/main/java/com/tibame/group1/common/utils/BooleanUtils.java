package com.tibame.group1.common.utils;

/**
 * 布林處理共用
 *
 * @author Jimmy Kang
 */
public class BooleanUtils {

    /**
     * 轉換字串為布林(轉換失敗為false)
     *
     * @param string 字串
     * @return 布林
     */
    public static boolean toBoolean(String string) {
        try {
            return Boolean.parseBoolean(string);
        } catch (Exception e) {
            return false;
        }
    }
}
