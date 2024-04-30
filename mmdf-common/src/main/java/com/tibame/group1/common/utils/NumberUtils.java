package com.tibame.group1.common.utils;

import java.math.BigDecimal;

/**
 * 數字處理共用
 *
 * @author Jimmy Kang
 */
public class NumberUtils {

    /**
     * 轉換字串為數字(轉換失敗為0)
     *
     * @param string 字串
     * @return 數字
     */
    public static int toInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 轉換字串為Double(轉換失敗為0)
     *
     * @param string 字串
     * @return Double
     */
    public static double toDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 轉換字串為BigDecimal(轉換失敗為0)
     *
     * @param string 字串
     * @return BigDecimal
     */
    public static BigDecimal toBigDecimal(String string) {
        return BigDecimal.valueOf(toDouble(string));
    }

    /**
     * 檢查是否為數字
     *
     * @param string 字串
     * @return 檢查結果
     */
    public static boolean isNumber(String string) {
        try {
            Float.parseFloat(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
