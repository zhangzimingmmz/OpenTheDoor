package cn.zhangziming.auth.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import cn.zhangziming.auth.security.jwt.JwtProperties;

/**
 * Spring Security基础配置
 * F
 * <p>配置密码编码器、认证管理器等基础组件
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    /**
     * 密码编码器
     * 使用BCrypt算法
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

