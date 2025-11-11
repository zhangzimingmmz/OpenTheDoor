package cn.zhangziming.auth.security.context;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户上下文
 * 
 * <p>基于ThreadLocal存储当前请求的用户信息
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
public class UserContext {

    /**
     * ThreadLocal存储用户信息
     */
    private static final ThreadLocal<UserInfo> USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前用户信息
     *
     * @param userInfo 用户信息
     */
    public static void setCurrentUser(UserInfo userInfo) {
        USER_THREAD_LOCAL.set(userInfo);
        log.debug("设置用户上下文: userId={}, username={}, tenantId={}", 
                userInfo.getUserId(), userInfo.getUsername(), userInfo.getTenantId());
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息，如果未登录则返回null
     */
    public static UserInfo getCurrentUser() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 获取当前用户ID
     *
     * @return 用户ID，如果未登录则返回null
     */
    public static Long getUserId() {
        UserInfo userInfo = USER_THREAD_LOCAL.get();
        return userInfo != null ? userInfo.getUserId() : null;
    }

    /**
     * 获取当前用户名
     *
     * @return 用户名，如果未登录则返回null
     */
    public static String getUsername() {
        UserInfo userInfo = USER_THREAD_LOCAL.get();
        return userInfo != null ? userInfo.getUsername() : null;
    }

    /**
     * 获取当前租户ID
     *
     * @return 租户ID，如果未登录则返回null
     */
    public static String getTenantId() {
        UserInfo userInfo = USER_THREAD_LOCAL.get();
        return userInfo != null ? userInfo.getTenantId() : null;
    }

    /**
     * 获取当前用户类型
     *
     * @return 用户类型，如果未登录则返回null
     */
    public static Integer getUserType() {
        UserInfo userInfo = USER_THREAD_LOCAL.get();
        return userInfo != null ? userInfo.getUserType() : null;
    }

    /**
     * 判断当前用户是否已登录
     *
     * @return true-已登录 false-未登录
     */
    public static boolean isLogin() {
        return USER_THREAD_LOCAL.get() != null;
    }

    /**
     * 判断当前用户是否是管理员
     *
     * @return true-是管理员 false-不是管理员
     */
    public static boolean isAdmin() {
        UserInfo userInfo = USER_THREAD_LOCAL.get();
        return userInfo != null && userInfo.isAdmin();
    }

    /**
     * 判断当前用户是否是超级管理员
     *
     * @return true-是超级管理员 false-不是超级管理员
     */
    public static boolean isSuperAdmin() {
        UserInfo userInfo = USER_THREAD_LOCAL.get();
        return userInfo != null && userInfo.isSuperAdmin();
    }

    /**
     * 判断当前用户是否拥有指定角色
     *
     * @param role 角色编码
     * @return true-拥有 false-不拥有
     */
    public static boolean hasRole(String role) {
        UserInfo userInfo = USER_THREAD_LOCAL.get();
        return userInfo != null && userInfo.hasRole(role);
    }

    /**
     * 判断当前用户是否拥有指定权限
     *
     * @param permission 权限编码
     * @return true-拥有 false-不拥有
     */
    public static boolean hasPermission(String permission) {
        UserInfo userInfo = USER_THREAD_LOCAL.get();
        return userInfo != null && userInfo.hasPermission(permission);
    }

    /**
     * 清除当前用户信息
     * 注意: 请求结束后必须调用此方法，否则会导致内存泄漏
     */
    public static void clear() {
        UserInfo userInfo = USER_THREAD_LOCAL.get();
        if (userInfo != null) {
            log.debug("清除用户上下文: userId={}, username={}", 
                    userInfo.getUserId(), userInfo.getUsername());
        }
        USER_THREAD_LOCAL.remove();
    }
}

