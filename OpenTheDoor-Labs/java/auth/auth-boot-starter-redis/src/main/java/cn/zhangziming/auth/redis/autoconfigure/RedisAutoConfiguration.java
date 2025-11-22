package cn.zhangziming.auth.redis.autoconfigure;

import cn.zhangziming.auth.redis.config.RedisConfig;
import cn.zhangziming.auth.redis.lock.RedisLock;
import cn.zhangziming.auth.redis.service.RedisService;
import cn.zhangziming.auth.redis.service.impl.RedisServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

/**
 * Redis 自动配置
 * 
 * <p>自动配置Redis相关功能
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@AutoConfiguration
@ConditionalOnClass(name = "org.springframework.data.redis.core.RedisTemplate")
@ConditionalOnProperty(prefix = "auth-boot.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import({RedisConfig.class, RedisServiceImpl.class, RedisLock.class})
public class RedisAutoConfiguration {
    
    // 配置类，主要通过@Import引入其他配置
}

