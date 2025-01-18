package com.example.dipexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisCacheRepository implements CacheRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisCacheRepository(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T findObject(String key, Class<T> tClass) {
        Object value = redisTemplate.opsForValue()
                .get(key);

        return objectMapper.convertValue(value, tClass);
    }

    @Override
    public void save(String key, Object value, Duration expiryTime) {
        redisTemplate.opsForValue()
                .set(key, value, expiryTime);
    }
}
