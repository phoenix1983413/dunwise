package com.dunwise.mica.redis;

import com.dunwise.mica.core.exception.DunwiseException;
import com.dunwise.mica.core.constant.CommonConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 */
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public <T> T get(String key) {
        try {
            return (T) redisTemplate.opsForValue().get(key);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public void setHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public <T> T getHash(String key, String hashKey) {
        try {
            return (T) redisTemplate.opsForHash().get(key, hashKey);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public Map<Object, Object> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void leftPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    public <T> T rightPop(String key) {
        try {
            return (T) redisTemplate.opsForList().rightPop(key);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public <T> T rightPop(String key, long timeout, TimeUnit unit) {
        try {
            return (T) redisTemplate.opsForList().rightPop(key, timeout, unit);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public Long getListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public Boolean lock(String key) {
        return redisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
    }

    public Boolean lock(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(key, "1", timeout, unit);
    }

    public void unlock(String key) {
        redisTemplate.delete(key);
    }

    public <T> T eval(String script, List<String> keys, Object... args) {
        RedisScript<T> redisScript = new DefaultRedisScript<>(script, (Class<T>) Object.class);
        return redisTemplate.execute(redisScript, keys, args);
    }
}
