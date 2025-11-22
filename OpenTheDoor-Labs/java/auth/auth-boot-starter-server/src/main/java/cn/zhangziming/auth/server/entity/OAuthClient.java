package cn.zhangziming.auth.server.entity;

import cn.zhangziming.auth.mybatis.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OAuth客户端实体
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("oauth_client")
public class OAuthClient extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端标识
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 资源ID集合
     */
    private String resourceIds;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 授权类型
     * authorization_code,password,refresh_token,client_credentials
     */
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    private String webServerRedirectUri;

    /**
     * 权限
     */
    private String authorities;

    /**
     * AccessToken有效期(秒)
     */
    private Integer accessTokenValidity;

    /**
     * RefreshToken有效期(秒)
     */
    private Integer refreshTokenValidity;

    /**
     * 附加信息
     */
    private String additionalInformation;

    /**
     * 是否自动授权
     * 0-否 1-是
     */
    private Integer autoApprove;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 状态
     * 0-禁用 1-正常
     */
    private Integer status;
}

