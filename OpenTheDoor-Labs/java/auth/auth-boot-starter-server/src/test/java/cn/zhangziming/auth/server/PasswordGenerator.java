package cn.zhangziming.auth.server;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码生成工具
 */
public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin123";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("========================================");
        System.out.println("原始密码: " + rawPassword);
        System.out.println("BCrypt哈希: " + encodedPassword);
        System.out.println("========================================");
        System.out.println("");
        System.out.println("SQL更新语句:");
        System.out.println("UPDATE sys_user SET password='" + encodedPassword + "' WHERE username='admin';");
        System.out.println("========================================");
    }
}


