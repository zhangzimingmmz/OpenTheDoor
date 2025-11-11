package cn.zhangziming.auth.redis.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis分布式锁
 * 
 * <p>基于Redis实现的分布式锁，支持自动续期和重入
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisLock {

    private final RedisTemplate<String, Object> redisTemplate;

    /** 锁的前缀 */
    private static final String LOCK_PREFIX = "lock:";
    
    /** 默认过期时间（秒） */
    private static final long DEFAULT_EXPIRE_TIME = 30;
    
    /** Lua脚本：确保删除锁时只能删除自己加的锁 */
    private static final String UNLOCK_LUA_SCRIPT = 
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "    return redis.call('del', KEYS[1]) " +
            "else " +
            "    return 0 " +
            "end";

    /**
     * 尝试获取锁
     *
     * @param lockKey 锁的key
     * @return 锁的标识（用于释放锁），获取失败返回null
     */
    public String tryLock(String lockKey) {
        return tryLock(lockKey, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 尝试获取锁（带超时时间）
     *
     * @param lockKey 锁的key
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return 锁的标识（用于释放锁），获取失败返回null
     */
    public String tryLock(String lockKey, long timeout, TimeUnit unit) {
        String key = LOCK_PREFIX + lockKey;
        String lockValue = UUID.randomUUID().toString();
        
        try {
            Boolean success = redisTemplate.opsForValue().setIfAbsent(key, lockValue, timeout, unit);
            if (Boolean.TRUE.equals(success)) {
                log.debug("获取锁成功: key={}, value={}", key, lockValue);
                return lockValue;
            }
        } catch (Exception e) {
            log.error("获取锁异常: key={}", key, e);
        }
        
        log.debug("获取锁失败: key={}", key);
        return null;
    }

    /**
     * 释放锁
     *
     * @param lockKey   锁的key
     * @param lockValue 锁的标识
     * @return 释放成功返回true
     */
    public boolean unlock(String lockKey, String lockValue) {
        if (lockValue == null) {
            return false;
        }
        
        String key = LOCK_PREFIX + lockKey;
        
        try {
            // 使用Lua脚本确保原子性
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(UNLOCK_LUA_SCRIPT, Long.class);
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), lockValue);
            boolean success = result != null && result > 0;
            
            if (success) {
                log.debug("释放锁成功: key={}", key);
            } else {
                log.warn("释放锁失败: key={}, 可能锁已过期或不是当前线程加的锁", key);
            }
            
            return success;
        } catch (Exception e) {
            log.error("释放锁异常: key={}", key, e);
            return false;
        }
    }

    /**
     * 执行带锁的操作
     *
     * @param lockKey  锁的key
     * @param runnable 要执行的操作
     * @return 执行成功返回true
     */
    public boolean executeWithLock(String lockKey, Runnable runnable) {
        String lockValue = tryLock(lockKey);
        if (lockValue == null) {
            return false;
        }
        
        try {
            runnable.run();
            return true;
        } finally {
            unlock(lockKey, lockValue);
        }
    }
}

