package cn.zhangziming.auth.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * 
 * <p>提供JWT的生成、解析、验证功能
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    /** Token类型：访问令牌 */
    public static final String TOKEN_TYPE_ACCESS = "access";
    
    /** Token类型：刷新令牌 */
    public static final String TOKEN_TYPE_REFRESH = "refresh";

    /**
     * 生成AccessToken
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param tenantId 租户ID
     * @return AccessToken
     */
    public String generateToken(Long userId, String username, String tenantId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("tenantId", tenantId);
        claims.put("tokenType", TOKEN_TYPE_ACCESS);
        
        return createToken(claims, username, jwtProperties.getExpireTime());
    }

    /**
     * 生成RefreshToken
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param tenantId 租户ID
     * @return RefreshToken
     */
    public String generateRefreshToken(Long userId, String username, String tenantId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("tenantId", tenantId);
        claims.put("tokenType", TOKEN_TYPE_REFRESH);
        
        return createToken(claims, username, jwtProperties.getRefreshExpireTime());
    }

    /**
     * 创建Token
     *
     * @param claims     自定义声明
     * @param subject    主题（通常是用户名）
     * @param expireTime 过期时间（秒）
     * @return Token字符串
     */
    private String createToken(Map<String, Object> claims, String subject, Long expireTime) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expireTime * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(jwtProperties.getIssuer())
                .setAudience(jwtProperties.getAudience())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析Token
     *
     * @param token Token字符串
     * @return Claims
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.warn("Token已过期: {}", e.getMessage());
            throw new RuntimeException("Token已过期");
        } catch (MalformedJwtException e) {
            log.warn("Token格式错误: {}", e.getMessage());
            throw new RuntimeException("Token格式错误");
        } catch (SignatureException e) {
            log.warn("Token签名验证失败: {}", e.getMessage());
            throw new RuntimeException("Token签名验证失败");
        } catch (UnsupportedJwtException e) {
            log.warn("不支持的Token: {}", e.getMessage());
            throw new RuntimeException("不支持的Token");
        } catch (IllegalArgumentException e) {
            log.warn("Token参数错误: {}", e.getMessage());
            throw new RuntimeException("Token参数错误");
        }
    }

    /**
     * 验证Token是否有效
     *
     * @param token Token字符串
     * @return true-有效 false-无效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从Token中提取用户ID
     *
     * @param token Token字符串
     * @return 用户ID
     */
    public Long extractUserId(String token) {
        Claims claims = parseToken(token);
        Object userId = claims.get("userId");
        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        }
        return (Long) userId;
    }

    /**
     * 从Token中提取用户名
     *
     * @param token Token字符串
     * @return 用户名
     */
    public String extractUsername(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 从Token中提取租户ID
     *
     * @param token Token字符串
     * @return 租户ID
     */
    public String extractTenantId(String token) {
        Claims claims = parseToken(token);
        return (String) claims.get("tenantId");
    }

    /**
     * 从Token中提取所有自定义声明
     *
     * @param token Token字符串
     * @return 自定义声明Map
     */
    public Map<String, Object> extractClaims(String token) {
        Claims claims = parseToken(token);
        return new HashMap<>(claims);
    }

    /**
     * 检查Token是否即将过期
     *
     * @param token          Token字符串
     * @param thresholdSeconds 阈值（秒）
     * @return true-即将过期 false-未即将过期
     */
    public boolean isTokenExpiringSoon(String token, long thresholdSeconds) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();
            Date now = new Date();
            long remainingTime = (expiration.getTime() - now.getTime()) / 1000;
            return remainingTime < thresholdSeconds;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 从请求头中提取Token
     *
     * @param authorizationHeader 认证请求头
     * @return Token字符串，如果无效则返回null
     */
    public String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(jwtProperties.getPrefix())) {
            return authorizationHeader.substring(jwtProperties.getPrefix().length());
        }
        return null;
    }

    /**
     * 获取签名密钥
     *
     * @return SecretKey
     */
    private SecretKey getSignKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

