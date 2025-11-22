package cn.zhangziming.auth.redis.constant;

/**
 * Redis缓存常量
 * 
 * <p>定义缓存key前缀和过期时间
 *
 * @author zhangziming
 * @since 2024-10-29
 */
public interface CacheConstant {

    /** 用户缓存前缀 */
    String USER_CACHE_PREFIX = "user:";
    
    /** Token缓存前缀 */
    String TOKEN_CACHE_PREFIX = "token:";
    
    /** 权限缓存前缀 */
    String PERMISSION_CACHE_PREFIX = "permission:";
    
    /** 角色缓存前缀 */
    String ROLE_CACHE_PREFIX = "role:";
    
    /** 菜单缓存前缀 */
    String MENU_CACHE_PREFIX = "menu:";
    
    /** 验证码缓存前缀 */
    String CAPTCHA_PREFIX = "captcha:";
    
    /** 登录锁定缓存前缀 */
    String LOGIN_LOCK_PREFIX = "login:lock:";
    
    /** 默认过期时间（秒）- 2小时 */
    long DEFAULT_EXPIRE_TIME = 7200L;
    
    /** Token过期时间（秒）- 7天 */
    long TOKEN_EXPIRE_TIME = 604800L;
    
    /** 验证码过期时间（秒）- 5分钟 */
    long CAPTCHA_EXPIRE_TIME = 300L;
    
    /** 登录锁定时间（秒）- 30分钟 */
    long LOGIN_LOCK_TIME = 1800L;
}

