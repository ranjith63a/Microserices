package com.microservices.student.service;

import com.microservices.student.client.DepartmentClient;
import com.microservices.student.dto.response.DepartmentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class DepartmentCacheService {

    @Autowired
    private DepartmentClient departmentClient;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    // ── @Cacheable — fetch from Redis, call method only on miss ──
    @Cacheable(value = "departments", key = "#code")
    public DepartmentResponse getDepartment(String code) {
        log.info("Cache MISS — fetching from department service: {}", code);
        return departmentClient.getDepartmentByCode(code);
    }

    // ── @CacheEvict — remove from cache when data changes ────────
    @CacheEvict(value = "departments", key = "#code")
    public void evictDepartment(String code) {
        log.info("Cache evicted for department: {}", code);
    }

    // ── @CachePut — update cache without skipping method ─────────
    @CachePut(value = "departments", key = "#response.code")
    public DepartmentResponse updateDepartmentCache(DepartmentResponse response) {
        return response;
    }

    // ── Manual Redis operation — store valid codes as a Set ───────
    private static final String VALID_CODES_KEY = "valid:department:codes";

    public void refreshValidCodes() {
        List<DepartmentResponse> all = departmentClient.getAllDepartments();

        if (all == null || all.isEmpty()) {
            log.warn("Skipping cache refresh — department service returned empty");
            return;
        }

        // Store valid codes Set (for quick validation)
        redisTemplate.delete(VALID_CODES_KEY);
        all.forEach(dept ->
                redisTemplate.opsForSet().add(VALID_CODES_KEY, dept.getDepartmentCode())
        );
        redisTemplate.expire(VALID_CODES_KEY, Duration.ofHours(2));

        // ✅ Also store full object for each department (for fallback data)
        all.forEach(dept -> {
            redisTemplate.opsForValue().set(
                    "departments::" + dept.getDepartmentCode(),  // same key @Cacheable uses
                    dept,
                    Duration.ofHours(2)
            );
            log.info("Cached full object for: {}", dept.getDepartmentCode());
        });

        log.info("Valid department codes cached: {}", all.size());
    }

    public boolean isValidCode(String code) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForSet().isMember(VALID_CODES_KEY, code)
        );
    }

    public boolean isCacheAvailable() {
        return Boolean.TRUE.equals(redisTemplate.hasKey(VALID_CODES_KEY));
    }

    public DepartmentResponse getDepartmentFromCache(String code) {
        DepartmentResponse cached = (DepartmentResponse) redisTemplate
                .opsForValue()
                .get("departments::" + code);

        if (cached != null) {
            log.info("Fetched from Redis cache: {}", code);
            return cached;
        }

        log.warn("Department {} not in cache — returning partial", code);
        return DepartmentResponse.builder()
                .id(0L)
                .departmentCode(code)
                .departmentName("Pending Verification")
                .build();
    }
}