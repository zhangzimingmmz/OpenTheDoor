package cn.zhangziming.auth.security.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 用户上下文信息
 * 
 * <p>存储在ThreadLocal中的用户信息
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {

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
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户类型
     * 1-普通用户 2-管理员 3-超级管理员
     */
    private Integer userType;

    /**
     * 角色编码列表
     */
    private Set<String> roles;

    /**
     * 权限编码列表
     */
    private Set<String> permissions;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 是否是管理员
     */
    public boolean isAdmin() {
        return userType != null && userType >= 2;
    }

    /**
     * 是否是超级管理员
     */
    public boolean isSuperAdmin() {
        return userType != null && userType == 3;
    }

    /**
     * 是否拥有指定角色
     */
    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }

    /**
     * 是否拥有任一角色
     */
    public boolean hasAnyRole(String... roles) {
        if (this.roles == null || roles == null) {
            return false;
        }
        for (String role : roles) {
            if (this.roles.contains(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否拥有所有角色
     */
    public boolean hasAllRoles(String... roles) {
        if (this.roles == null || roles == null) {
            return false;
        }
        for (String role : roles) {
            if (!this.roles.contains(role)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否拥有指定权限
     */
    public boolean hasPermission(String permission) {
        return permissions != null && permissions.contains(permission);
    }

    /**
     * 是否拥有任一权限
     */
    public boolean hasAnyPermission(String... permissions) {
        if (this.permissions == null || permissions == null) {
            return false;
        }
        for (String permission : permissions) {
            if (this.permissions.contains(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否拥有所有权限
     */
    public boolean hasAllPermissions(String... permissions) {
        if (this.permissions == null || permissions == null) {
            return false;
        }
        for (String permission : permissions) {
            if (!this.permissions.contains(permission)) {
                return false;
            }
        }
        return true;
    }
}

