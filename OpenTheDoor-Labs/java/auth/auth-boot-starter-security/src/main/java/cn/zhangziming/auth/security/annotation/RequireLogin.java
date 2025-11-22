package cn.zhangziming.auth.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 要求登录注解
 * 
 * <p>标注在类或方法上，表示需要登录才能访问
 * <p>客户端模块的拦截器或切面会检查此注解
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireLogin {

    /**
     * 是否必须登录
     * 默认true，如果设置为false则忽略此注解
     */
    boolean required() default true;
}

