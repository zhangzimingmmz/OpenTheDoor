package cn.zhangziming.auth.server.config;

import cn.zhangziming.auth.security.jwt.JwtUtil;
import cn.zhangziming.auth.server.filter.ServerJwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security FilterChain配置
 * 
 * <p>配置哪些接口需要认证，哪些接口public访问
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("========================================");
        System.out.println("✅ SecurityFilterChainConfig 正在加载...");
        System.out.println("========================================");
        
        http
                // 禁用CSRF（因为使用JWT）
                .csrf(csrf -> csrf.disable())
                
                // 配置Session管理为无状态
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // 添加JWT认证过滤器（Server版本，会设置SecurityContext）
                .addFilterBefore(new ServerJwtAuthenticationFilter(jwtUtil), 
                        UsernamePasswordAuthenticationFilter.class)
                
                // 配置权限
                .authorizeHttpRequests(auth -> auth
                        // 公开接口 - 使用更宽松的匹配
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/doc.html", "/webjars/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        // 其他接口需要认证
                        .anyRequest().authenticated()
                );
        
        System.out.println("✅ SecurityFilterChain 配置完成！（已添加JWT过滤器）");
        return http.build();
    }
}

