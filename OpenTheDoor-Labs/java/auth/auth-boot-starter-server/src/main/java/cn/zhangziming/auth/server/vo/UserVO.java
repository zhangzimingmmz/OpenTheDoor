package cn.zhangziming.auth.server.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 用户视图对象
 * 
 * <p>不包含敏感信息(如密码)
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 性别
     * 0-未知 1-男 2-女
     */
    private Integer gender;

    /**
     * 状态
     * 0-禁用 1-正常 2-锁定
     */
    private Integer status;

    /**
     * 用户类型
     * 1-普通用户 2-管理员 3-超级管理员
     */
    private Integer userType;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 角色列表
     */
    private List<RoleVO> roles;

    /**
     * 权限列表
     */
    private List<PermissionVO> permissions;

    /**
     * 角色编码集合
     */
    private Set<String> roleCodes;

    /**
     * 权限编码集合
     */
    private Set<String> permissionCodes;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}

