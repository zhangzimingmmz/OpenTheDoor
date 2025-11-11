package cn.zhangziming.auth.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全工具类
 * 
 * <p>提供密码加密、Spring Security认证信息获取等功能
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
public class SecurityUtil {

    /**
     * 密码编码器（单例）
     */
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /**
     * 加密密码
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        return PASSWORD_ENCODER.encode(rawPassword);
    }

    /**
     * 校验密码
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return true-匹配 false-不匹配
     */
    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        try {
            return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
        } catch (Exception e) {
            log.error("密码校验异常", e);
            return false;
        }
    }

    /**
     * 获取当前认证信息
     *
     * @return Authentication对象，如果未认证则返回null
     */
    public static Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 判断当前是否已认证
     *
     * @return true-已认证 false-未认证
     */
    public static boolean isAuthenticated() {
        Authentication authentication = getCurrentAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * 获取当前认证用户的用户名
     *
     * @return 用户名，如果未认证则返回null
     */
    public static String getCurrentUsername() {
        Authentication authentication = getCurrentAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                return (String) principal;
            }
            // 如果是UserDetails对象，调用getUsername()
            try {
                return (String) principal.getClass().getMethod("getUsername").invoke(principal);
            } catch (Exception e) {
                log.warn("无法从Principal中获取用户名: {}", e.getMessage());
            }
        }
        return null;
    }

    /**
     * 清除当前认证信息
     */
    public static void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }

    /**
     * 生成随机盐值
     *
     * @param length 盐值长度
     * @return 随机盐值
     */
    public static String generateSalt(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            salt.append(chars.charAt(index));
        }
        return salt.toString();
    }

    /**
     * 校验密码强度
     *
     * @param password 密码
     * @return true-符合强度要求 false-不符合
     */
    public static boolean validatePasswordStrength(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        
        // 至少包含一个数字、一个小写字母、一个大写字母
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasUpper = password.matches(".*[A-Z].*");
        
        return hasDigit && hasLower && hasUpper;
    }

    /**
     * 获取密码编码器
     *
     * @return PasswordEncoder
     */
    public static PasswordEncoder getPasswordEncoder() {
        return PASSWORD_ENCODER;
    }
}

