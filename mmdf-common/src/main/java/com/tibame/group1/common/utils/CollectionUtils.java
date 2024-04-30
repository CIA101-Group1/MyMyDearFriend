package com.tibame.group1.common.utils;

import java.util.Collection;

/**
 * 陣列處理共用方法
 *
 * @author Jimmy Kang
 */
public class CollectionUtils {

    /**
     * 檢查陣列是否為空
     *
     * @param collection 陣列
     * @return 檢查結果
     */
    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 檢查陣列是否不為空
     *
     * @param collection 陣列
     * @return 檢查結果
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
