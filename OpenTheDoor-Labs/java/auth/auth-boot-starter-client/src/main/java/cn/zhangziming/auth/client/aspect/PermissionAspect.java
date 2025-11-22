package cn.zhangziming.auth.client.aspect;

import cn.zhangziming.auth.common.constant.ErrorCode;
import cn.zhangziming.auth.common.exception.BusinessException;
import cn.zhangziming.auth.security.annotation.RequireLogin;
import cn.zhangziming.auth.security.annotation.RequirePermission;
import cn.zhangziming.auth.security.annotation.RequireRole;
import cn.zhangziming.auth.security.context.UserContext;
import cn.zhangziming.auth.security.context.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * 权限切面
 * 
 * <p>处理权限注解，进行权限校验
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@Aspect
@Order(100)
@Component
public class PermissionAspect {

    /**
     * 处理RequireLogin注解
     */
    @Before("@annotation(cn.zhangziming.auth.security.annotation.RequireLogin)")
    public void checkLogin(JoinPoint joinPoint) {
        UserInfo userInfo = UserContext.getCurrentUser();
        
        if (userInfo == null || userInfo.getUserId() == null) {
            log.warn("用户未登录，拒绝访问: {}", joinPoint.getSignature().toLongString());
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
        }
        
        log.debug("登录校验通过: userId={}", userInfo.getUserId());
    }

    /**
     * 处理RequirePermission注解
     */
    @Before("@annotation(cn.zhangziming.auth.security.annotation.RequirePermission)")
    public void checkPermission(JoinPoint joinPoint) {
        // 1. 先检查登录
        checkLogin(joinPoint);
        
        // 2. 获取注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequirePermission annotation = method.getAnnotation(RequirePermission.class);
        
        if (annotation == null) {
            return;
        }
        
        // 3. 获取用户权限
        UserInfo userInfo = UserContext.getCurrentUser();
        Set<String> userPermissions = userInfo.getPermissions();
        
        if (userPermissions == null || userPermissions.isEmpty()) {
            log.warn("用户无任何权限，拒绝访问: userId={}, method={}", 
                    userInfo.getUserId(), joinPoint.getSignature().toLongString());
            throw new BusinessException(ErrorCode.NO_PERMISSION, "无访问权限");
        }
        
        // 4. 校验权限
        String[] requiredPermissions = annotation.value();
        RequirePermission.Logical logical = annotation.logical();
        
        boolean hasPermission;
        if (logical == RequirePermission.Logical.AND) {
            // AND逻辑：需要拥有所有权限
            hasPermission = Arrays.stream(requiredPermissions)
                    .allMatch(userPermissions::contains);
        } else {
            // OR逻辑：拥有任一权限即可
            hasPermission = Arrays.stream(requiredPermissions)
                    .anyMatch(userPermissions::contains);
        }
        
        if (!hasPermission) {
            log.warn("用户权限不足，拒绝访问: userId={}, required={}, logical={}, method={}", 
                    userInfo.getUserId(), Arrays.toString(requiredPermissions), 
                    logical, joinPoint.getSignature().toLongString());
            throw new BusinessException(ErrorCode.NO_PERMISSION, "权限不足");
        }
        
        log.debug("权限校验通过: userId={}, permissions={}", 
                userInfo.getUserId(), Arrays.toString(requiredPermissions));
    }

    /**
     * 处理RequireRole注解
     */
    @Before("@annotation(cn.zhangziming.auth.security.annotation.RequireRole)")
    public void checkRole(JoinPoint joinPoint) {
        // 1. 先检查登录
        checkLogin(joinPoint);
        
        // 2. 获取注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequireRole annotation = method.getAnnotation(RequireRole.class);
        
        if (annotation == null) {
            return;
        }
        
        // 3. 获取用户角色
        UserInfo userInfo = UserContext.getCurrentUser();
        Set<String> userRoles = userInfo.getRoles();
        
        if (userRoles == null || userRoles.isEmpty()) {
            log.warn("用户无任何角色，拒绝访问: userId={}, method={}", 
                    userInfo.getUserId(), joinPoint.getSignature().toLongString());
            throw new BusinessException(ErrorCode.NO_PERMISSION, "无访问权限");
        }
        
        // 4. 校验角色
        String[] requiredRoles = annotation.value();
        RequireRole.Logical logical = annotation.logical();
        
        boolean hasRole;
        if (logical == RequireRole.Logical.AND) {
            // AND逻辑：需要拥有所有角色
            hasRole = Arrays.stream(requiredRoles)
                    .allMatch(userRoles::contains);
        } else {
            // OR逻辑：拥有任一角色即可
            hasRole = Arrays.stream(requiredRoles)
                    .anyMatch(userRoles::contains);
        }
        
        if (!hasRole) {
            log.warn("用户角色不足，拒绝访问: userId={}, required={}, logical={}, method={}", 
                    userInfo.getUserId(), Arrays.toString(requiredRoles), 
                    logical, joinPoint.getSignature().toLongString());
            throw new BusinessException(ErrorCode.NO_PERMISSION, "角色不足");
        }
        
        log.debug("角色校验通过: userId={}, roles={}", 
                userInfo.getUserId(), Arrays.toString(requiredRoles));
    }
}

