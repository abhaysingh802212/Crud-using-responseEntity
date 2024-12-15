package com.mockito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, 10, TimeUnit.MINUTES); // 10 minutes TTL (Time to live)
    }

    // Get a value from Redis
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Check if a key exists in Redis
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    // Delete a key from Redis
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    // Set a value in Redis if not already set
    public boolean setIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }
}

