package cn.zhangziming.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Auth Server启动类
 * 
 * <p>认证授权服务端应用
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
