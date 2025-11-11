package cn.zhangziming.auth.common.util;

import cn.zhangziming.auth.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

/**
 * 参数校验工具类
 * 
 * @author zhangziming
 * @since 1.0.0
 */
public class ValidationUtil {
    
    /**
     * 断言对象不为null
     * 
     * @param obj 对象
     * @param message 错误信息
     */
    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new BusinessException(message);
        }
    }
    
    /**
     * 断言字符串不为空
     * 
     * @param str 字符串
     * @param message 错误信息
     */
    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(message);
        }
    }
    
    /**
     * 断言条件为true
     * 
     * @param condition 条件
     * @param message 错误信息
     */
    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new BusinessException(message);
        }
    }
    
    /**
     * 断言条件为false
     * 
     * @param condition 条件
     * @param message 错误信息
     */
    public static void isFalse(boolean condition, String message) {
        if (condition) {
            throw new BusinessException(message);
        }
    }
    
    /**
     * 断言对象不为null（带错误码）
     * 
     * @param obj 对象
     * @param code 错误码
     * @param message 错误信息
     */
    public static void notNull(Object obj, Integer code, String message) {
        if (obj == null) {
            throw new BusinessException(code, message);
        }
    }
    
    /**
     * 断言字符串不为空（带错误码）
     * 
     * @param str 字符串
     * @param code 错误码
     * @param message 错误信息
     */
    public static void notBlank(String str, Integer code, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(code, message);
        }
    }
    
    /**
     * 断言条件为true（带错误码）
     * 
     * @param condition 条件
     * @param code 错误码
     * @param message 错误信息
     */
    public static void isTrue(boolean condition, Integer code, String message) {
        if (!condition) {
            throw new BusinessException(code, message);
        }
    }
}

