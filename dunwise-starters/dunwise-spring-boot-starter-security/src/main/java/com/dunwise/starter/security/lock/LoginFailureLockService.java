package com.dunwise.starter.security.lock;

import com.dunwise.mica.core.constant.CommonConstants;
import com.dunwise.mica.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 登录失败锁定服务
 */
@Component
public class LoginFailureLockService {

    private final RedisUtils redisUtils;

    @Value("${dunwise.security.login.max-attempts:5}")
    private int maxAttempts;

    @Value("${dunwise.security.login.lock-duration:1800}")
    private long lockDurationSeconds;

    public LoginFailureLockService(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    public void recordFailure(String username) {
        String key = "login:fail:" + username;
        Long count = redisUtils.increment(key);
        if (count == 1) {
            redisUtils.expire(key, lockDurationSeconds, TimeUnit.SECONDS);
        }
    }

    public boolean isLocked(String username) {
        String key = "login:fail:" + username;
        Long count = redisUtils.get(key);
        return count != null && count >= maxAttempts;
    }

    public void clearFailure(String username) {
        String key = "login:fail:" + username;
        redisUtils.delete(key);
    }

    public long getRemainingLockTime(String username) {
        String key = "login:fail:" + username;
        Long expire = redisUtils.getExpire(key);
        return expire != null ? expire : 0;
    }

    public int getFailureCount(String username) {
        String key = "login:fail:" + username;
        Long count = redisUtils.get(key);
        return count != null ? count.intValue() : 0;
    }
}
