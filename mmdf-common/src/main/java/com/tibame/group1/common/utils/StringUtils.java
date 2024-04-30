package com.tibame.group1.common.utils;

/**
 * 常用轉換共用方法
 *
 * @author peihui
 */
public class StringUtils {
    public static final String SPACE = " ";

    public static final String EMPTY = "";

    /**
     * 檢查字串是否為空
     *
     * @param string 字串
     * @return 檢查結果
     */
    public static boolean isEmpty(String string) {
        return null == string || string.isEmpty();
    }

    /**
     * 檢查字串是否不為空
     *
     * @param string 字串
     * @return 檢查結果
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * 清理字串內空白符號
     *
     * @param string 字串
     * @return 整理後字串
     */
    public static String normalizeSpace(final String string) {
        if (isEmpty(string)) {
            return string;
        }
        final int size = string.length();
        final char[] newChars = new char[size];
        int count = 0;
        int whitespacesCount = 0;
        boolean startWhitespaces = true;
        for (int i = 0; i < size; i++) {
            final char actualChar = string.charAt(i);
            final boolean isWhitespace = Character.isWhitespace(actualChar);
            if (isWhitespace) {
                if (whitespacesCount == 0 && !startWhitespaces) {
                    newChars[count++] = SPACE.charAt(0);
                }
                whitespacesCount++;
            } else {
                startWhitespaces = false;
                newChars[count++] = (actualChar == 160 ? 32 : actualChar);
                whitespacesCount = 0;
            }
        }
        if (startWhitespaces) {
            return EMPTY;
        }
        return new String(newChars, 0, count - (whitespacesCount > 0 ? 1 : 0)).trim();
    }
}
