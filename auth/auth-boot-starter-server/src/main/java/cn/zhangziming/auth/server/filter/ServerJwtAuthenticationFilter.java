package cn.zhangziming.auth.server.filter;

import cn.zhangziming.auth.common.constant.CommonConstant;
import cn.zhangziming.auth.security.context.UserContext;
import cn.zhangziming.auth.security.context.UserInfo;
import cn.zhangziming.auth.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Server端JWT认证过滤器
 * 
 * <p>从请求头中提取JWT Token，解析并设置Spring Security上下文
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@RequiredArgsConstructor
public class ServerJwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    /**
     * 不需要认证的路径
     */
    private static final Set<String> EXCLUDE_PATHS = new HashSet<>(Arrays.asList(
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh"
    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        
        // 跳过不需要认证的路径
        if (shouldSkipFilter(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 1. 从请求头获取Token
            String token = extractToken(request);

            if (token != null && !token.isEmpty()) {
                // 2. 验证Token
                if (jwtUtil.validateToken(token)) {
                    // 3. 解析Token获取用户信息
                    Claims claims = jwtUtil.parseToken(token);
                    
                    // 4. 构建用户上下文
                    UserInfo userInfo = buildUserInfo(claims);
                    
                    // 5. 设置到ThreadLocal
                    UserContext.setCurrentUser(userInfo);
                    
                    // 6. 设置Spring Security上下文（关键！）
                    List<SimpleGrantedAuthority> authorities = userInfo.getPermissions() != null ?
                            userInfo.getPermissions().stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList()) :
                            List.of();
                    
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userInfo.getUsername(),
                                    null,
                                    authorities
                            );
                    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    log.debug("JWT认证成功并设置Security上下文: userId={}, username={}", 
                            userInfo.getUserId(), userInfo.getUsername());
                } else {
                    log.warn("Token验证失败: {}", token);
                }
            }

            // 7. 继续过滤链
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("JWT认证失败", e);
            filterChain.doFilter(request, response);
        } finally {
            // 8. 清除上下文
            UserContext.clear();
            SecurityContextHolder.clearContext();
        }
    }

    /**
     * 从请求头提取Token
     */
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(CommonConstant.AUTHORIZATION_HEADER);
        
        if (bearerToken != null && bearerToken.startsWith(CommonConstant.TOKEN_PREFIX)) {
            return bearerToken.substring(CommonConstant.TOKEN_PREFIX.length());
        }
        
        return null;
    }

    /**
     * 构建用户信息
     */
    private UserInfo buildUserInfo(Claims claims) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(claims.get("userId", Long.class));
        userInfo.setUsername(claims.getSubject());
        userInfo.setTenantId(claims.get("tenantId", String.class));
        
        // 解析角色
        @SuppressWarnings("unchecked")
        List<String> roles = claims.get("roles", List.class);
        if (roles != null) {
            userInfo.setRoles(new HashSet<>(roles));
        }
        
        // 解析权限
        @SuppressWarnings("unchecked")
        List<String> permissions = claims.get("permissions", List.class);
        if (permissions != null) {
            userInfo.setPermissions(new HashSet<>(permissions));
        }
        
        return userInfo;
    }

    /**
     * 是否跳过过滤
     */
    private boolean shouldSkipFilter(String requestUri) {
        return EXCLUDE_PATHS.stream().anyMatch(requestUri::startsWith);
    }
}

