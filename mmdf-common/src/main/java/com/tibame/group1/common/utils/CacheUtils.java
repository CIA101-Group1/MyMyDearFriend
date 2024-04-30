package com.tibame.group1.common.utils;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * 快取處理共用
 *
 * @author Jimmy Kang
 */
public class CacheUtils {

    /**
     * 清理快取
     *
     * @param cacheManager CacheManager
     * @param cacheNames 快取名稱
     */
    public static void clear(CacheManager cacheManager, String... cacheNames) {
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            if (null != cache) {
                cache.clear();
            }
        }
    }
}
