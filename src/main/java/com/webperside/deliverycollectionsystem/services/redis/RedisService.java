package com.webperside.deliverycollectionsystem.services.redis;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedissonClient redissonClient;

    // SAVE (no TTL)
    public void save(String key, Object value) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    // SAVE with TTL
    public void save(String key, Object value, long ttl, TimeUnit unit) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value, ttl, unit);
    }

    // GET
    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(redissonClient.getBucket(key).get());
    }

    // UPDATE
    public void update(String key, Object value) {
        redissonClient.getBucket(key).set(value);
    }

    // DELETE
    public void delete(String key) {
        redissonClient.getBucket(key).delete();
    }

    // EXISTS
    public boolean exists(String key) {
        return redissonClient.getBucket(key).isExists();
    }

    // TTL
    public long getTtl(String key) {
        return redissonClient.getBucket(key).remainTimeToLive();
    }

    // EXPIRE
    public void expire(String key, long ttl, TimeUnit unit) {
        redissonClient.getBucket(key).expire(ttl, unit);
    }
}
