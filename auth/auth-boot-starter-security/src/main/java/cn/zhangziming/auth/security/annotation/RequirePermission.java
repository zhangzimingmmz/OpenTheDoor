package cn.zhangziming.auth.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 要求权限注解
 * 
 * <p>标注在类或方法上，表示需要特定权限才能访问
 * <p>支持单个权限或多个权限（与/或关系）
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {

    /**
     * 权限编码
     * 例如: "user:read", "user:write", "user:delete"
     */
    String[] value();

    /**
     * 权限关系
     * AND - 需要拥有所有权限
     * OR  - 拥有任一权限即可
     */
    Logical logical() default Logical.AND;

    /**
     * 权限逻辑关系枚举
     */
    enum Logical {
        /** 与 - 需要拥有所有权限 */
        AND,
        /** 或 - 拥有任一权限即可 */
        OR
    }
}

