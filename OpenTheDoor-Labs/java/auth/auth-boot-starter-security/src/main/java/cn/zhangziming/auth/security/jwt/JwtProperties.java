package cn.zhangziming.auth.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT配置属性
 * 
 * <p>从配置文件中读取JWT相关配置
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@ConfigurationProperties(prefix = "auth-boot.security.jwt")
public class JwtProperties {

    /**
     * JWT密钥
     * 建议使用至少256位的密钥
     */
    private String secret = "auth-boot-starter-default-secret-key-please-change-in-production";

    /**
     * AccessToken过期时间（秒）
     * 默认2小时
     */
    private Long expireTime = 7200L;

    /**
     * RefreshToken过期时间（秒）
     * 默认7天
     */
    private Long refreshExpireTime = 604800L;

    /**
     * Token请求头名称
     * 默认: Authorization
     */
    private String header = "Authorization";

    /**
     * Token前缀
     * 默认: Bearer 
     */
    private String prefix = "Bearer ";

    /**
     * 签发者
     */
    private String issuer = "auth-boot-starter";

    /**
     * 受众
     */
    private String audience = "auth-boot-client";
}

