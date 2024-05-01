package com.tibame.group1.web.config;

import com.tibame.group1.common.utils.RedisUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
@Slf4j
public class RedisConfig implements CacheErrorHandler {

    @Bean
    public KeyGenerator emsKeyGenerator() {
        return RedisUtils.createEmsKeyGenerator();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        return RedisUtils.createEmsTemplate(factory);
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        return RedisUtils.createEmsCacheManager(factory);
    }

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
        log.warn("Redis緩存處理錯誤，錯誤訊息 - " + e.getMessage());
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
        log.warn("Redis緩存處理錯誤，錯誤訊息 - " + e.getMessage());
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
        log.warn("Redis緩存處理錯誤，錯誤訊息 - " + e.getMessage());
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        log.warn("Redis緩存處理錯誤，錯誤訊息 - " + e.getMessage());
    }
}
