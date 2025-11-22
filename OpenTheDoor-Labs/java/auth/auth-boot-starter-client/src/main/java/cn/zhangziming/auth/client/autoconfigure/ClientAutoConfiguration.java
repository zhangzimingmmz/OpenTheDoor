package cn.zhangziming.auth.client.autoconfigure;

import cn.zhangziming.auth.client.aspect.PermissionAspect;
import cn.zhangziming.auth.client.filter.JwtAuthenticationFilter;
import cn.zhangziming.auth.security.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;

/**
 * 客户端自动配置
 * 
 * <p>自动配置JWT过滤器和权限切面
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy
@ConditionalOnProperty(prefix = "auth-boot.client", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ClientAutoConfiguration {

    public ClientAutoConfiguration() {
        log.info("======= Auth Boot Client Auto Configuration Initialized =======");
    }

    /**
     * 注册JWT认证过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter(JwtUtil jwtUtil) {
        FilterRegistrationBean<JwtAuthenticationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtAuthenticationFilter(jwtUtil));
        registration.addUrlPatterns("/*");
        registration.setName("jwtAuthenticationFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        
        log.info("JWT Authentication Filter registered");
        return registration;
    }

    /**
     * 注册权限切面
     */
    @Bean
    @ConditionalOnMissingBean
    public PermissionAspect permissionAspect() {
        log.info("Permission Aspect registered");
        return new PermissionAspect();
    }
}

