package cn.zhangziming.auth.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 要求角色注解
 * 
 * <p>标注在类或方法上，表示需要特定角色才能访问
 * <p>支持单个角色或多个角色（与/或关系）
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRole {

    /**
     * 角色编码
     * 例如: "ADMIN", "USER", "MANAGER"
     */
    String[] value();

    /**
     * 角色关系
     * AND - 需要拥有所有角色
     * OR  - 拥有任一角色即可
     */
    Logical logical() default Logical.AND;

    /**
     * 角色逻辑关系枚举
     */
    enum Logical {
        /** 与 - 需要拥有所有角色 */
        AND,
        /** 或 - 拥有任一角色即可 */
        OR
    }
}

