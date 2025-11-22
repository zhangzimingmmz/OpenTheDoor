package cn.zhangziming.auth.common.constant;

/**
 * 错误码常量
 * 
 * <p>错误码规范：
 * <ul>
 *   <li>2xx: 成功</li>
 *   <li>4xx: 客户端错误</li>
 *   <li>5xx: 服务端错误</li>
 *   <li>1000-1999: 认证相关错误</li>
 *   <li>2000-2999: 用户相关错误</li>
 *   <li>3000-3999: 权限相关错误</li>
 *   <li>4000-4999: 租户相关错误</li>
 * </ul>
 * 
 * @author zhangziming
 * @since 1.0.0
 */
public interface ErrorCode {
    
    // ==================== HTTP标准错误码 ====================
    
    /**
     * 请求参数错误
     */
    Integer BAD_REQUEST = 400;
    
    /**
     * 未认证
     */
    Integer UNAUTHORIZED = 401;
    
    /**
     * 无权限
     */
    Integer FORBIDDEN = 403;
    
    /**
     * 资源不存在
     */
    Integer NOT_FOUND = 404;
    
    /**
     * 服务器错误
     */
    Integer INTERNAL_SERVER_ERROR = 500;
    
    // ==================== 认证相关错误码 (1000-1999) ====================
    
    /**
     * 用户名或密码错误
     */
    Integer LOGIN_ERROR = 1001;
    
    /**
     * Token无效
     */
    Integer TOKEN_INVALID = 1002;
    
    /**
     * Token已过期
     */
    Integer TOKEN_EXPIRED = 1003;
    
    /**
     * 用户已被禁用
     */
    Integer USER_DISABLED = 1004;
    
    /**
     * 用户已被锁定
     */
    Integer USER_LOCKED = 1005;
    
    /**
     * 验证码错误
     */
    Integer CAPTCHA_ERROR = 1006;
    
    /**
     * 验证码已过期
     */
    Integer CAPTCHA_EXPIRED = 1007;
    
    // ==================== 用户相关错误码 (2000-2999) ====================
    
    /**
     * 用户不存在
     */
    Integer USER_NOT_FOUND = 2001;
    
    /**
     * 用户名已存在
     */
    Integer USERNAME_EXISTS = 2002;
    
    /**
     * 邮箱已存在
     */
    Integer EMAIL_EXISTS = 2003;
    
    /**
     * 手机号已存在
     */
    Integer PHONE_EXISTS = 2004;
    
    /**
     * 原密码错误
     */
    Integer OLD_PASSWORD_ERROR = 2005;
    
    /**
     * 密码错误
     */
    Integer PASSWORD_ERROR = 2006;
    
    // ==================== 权限相关错误码 (3000-3999) ====================
    
    /**
     * 无权限访问
     */
    Integer NO_PERMISSION = 3001;
    
    /**
     * 角色不存在
     */
    Integer ROLE_NOT_FOUND = 3002;
    
    /**
     * 权限不存在
     */
    Integer PERMISSION_NOT_FOUND = 3003;
    
    /**
     * 角色编码已存在
     */
    Integer ROLE_CODE_EXISTS = 3004;
    
    /**
     * 角色已被使用，无法删除
     */
    Integer ROLE_IN_USE = 3005;
    
    // ==================== 租户相关错误码 (4000-4999) ====================
    
    /**
     * 租户不存在
     */
    Integer TENANT_NOT_FOUND = 4001;
    
    /**
     * 租户已过期
     */
    Integer TENANT_EXPIRED = 4002;
    
    /**
     * 租户账号数量已达上限
     */
    Integer TENANT_ACCOUNT_LIMIT = 4003;
}

