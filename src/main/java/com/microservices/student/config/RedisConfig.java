package com.microservices.student.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * RedisConfig — activate with spring.profiles.active=redis
 *
 * Industry best practices:
 *  1. JSON serialization (not Java default) for debuggability
 *  2. Per-cache TTL via entryTtl overrides
 *  3. Null values allowed to cache "not found" results and prevent cache stampede
 *  4. String key serializer for human-readable Redis keys
 */
@Configuration
//@Profile("redis")
@EnableCaching
public class RedisConfig {

    // ── Step A: Connection Factory ──────────────────────────────
    // Spring Boot auto-creates this from application.yml
    // You only need to define it manually for advanced config
    // (SSL, cluster, sentinel) — for basic setup, skip this bean

    // ── Step B: RedisTemplate (for manual Redis operations) ─────
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // Key → plain String (human-readable in Redis CLI)
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Value → JSON (not Java serialized bytes — debuggable)
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    // ── Step C: CacheManager (@Cacheable support) ───────────────
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {

        // Default config applied to ALL caches
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))          // default TTL
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();               // don't cache nulls

        // Per-cache TTL overrides (best practice)
        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();

        cacheConfigs.put("departments",
                defaultConfig.entryTtl(Duration.ofHours(1)));   // departments change rarely

        cacheConfigs.put("students",
                defaultConfig.entryTtl(Duration.ofMinutes(30)));

        cacheConfigs.put("validDeptCodes",
                defaultConfig.entryTtl(Duration.ofHours(2)));   // used for fallback validation

        return RedisCacheManager.builder(factory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigs)
                .build();
    }
}
