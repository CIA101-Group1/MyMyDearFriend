package com.tibame.group1.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.StringJoiner;

/**
 * Redis共用方法
 *
 * @author Jimmy Kang
 */
public class RedisUtils {

    /**
     * 建立ems用客製Key生成器
     *
     * @return Key生成器
     */
    public static KeyGenerator createEmsKeyGenerator() {
        return createEmsKeyGenerator("");
    }

    /**
     * 建立ems用客製Key生成器
     *
     * @return Key生成器
     */
    public static KeyGenerator createEmsKeyGenerator(String hash) {
        return (target, method, params) -> {
            StringBuilder stringBuilder;
            if (StringUtils.isEmpty(hash)) {
                stringBuilder = new StringBuilder(method.getName());
            } else {
                stringBuilder = new StringBuilder(hash).append("-").append(method.getName());
            }

            StringJoiner stringJoiner = new StringJoiner(",");
            try {
                for (Object param : params) {
                    stringJoiner.add(
                            param.getClass().getSimpleName()
                                    + "="
                                    + JsonUtils.toJson(param).replace(":", "="));
                }
            } catch (JsonProcessingException e) {
                // 無法轉json的話用原始方法產
                for (Object param : params) {
                    if (null == param) {
                        stringJoiner.add("null");
                    } else {
                        stringJoiner.add(param.toString());
                    }
                }
            }
            stringBuilder.append(" [").append(stringJoiner).append("]");
            return stringBuilder.toString();
        };
    }

    /**
     * 建立ems用客製redis模板
     *
     * @param factory Redis連接序
     * @return redis模板
     */
    public static RedisTemplate<String, String> createEmsTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    /**
     * 建立ems用客製快取服務
     *
     * @param factory Redis連接序
     * @return 快取服務
     */
    public static CacheManager createEmsCacheManager(RedisConnectionFactory factory) {
        RedisSerializationContext.SerializationPair<String> keyPair =
                RedisSerializationContext.SerializationPair.fromSerializer(
                        new StringRedisSerializer());
        RedisSerializationContext.SerializationPair<Object> valuePair =
                RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJackson2JsonRedisSerializer());
        RedisCacheConfiguration defaultCacheConfig =
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(keyPair) // Key序列化方式
                        .serializeValuesWith(valuePair) // Value序列化方式
                        .entryTtl(Duration.ofHours(1)); // 過期時間
        RedisCacheManager redisCacheManager =
                RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(factory)
                        .cacheDefaults(defaultCacheConfig)
                        .build();
        redisCacheManager.setTransactionAware(false);
        return redisCacheManager;
    }
}
