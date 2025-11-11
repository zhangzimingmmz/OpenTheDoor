package cn.zhangziming.auth.security.autoconfigure;

import cn.zhangziming.auth.security.config.SecurityConfig;
import cn.zhangziming.auth.security.jwt.JwtProperties;
import cn.zhangziming.auth.security.jwt.JwtUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * Security自动配置
 * 
 * <p>自动配置Security相关功能
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@AutoConfiguration
@ConditionalOnClass(name = "org.springframework.security.core.Authentication")
@ConditionalOnProperty(prefix = "auth-boot.security", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(JwtProperties.class)
@Import({SecurityConfig.class, JwtUtil.class})
public class SecurityAutoConfiguration {
    
    // 配置类，主要通过@Import引入其他配置
}

