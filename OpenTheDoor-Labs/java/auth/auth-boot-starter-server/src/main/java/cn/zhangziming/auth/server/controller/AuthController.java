package cn.zhangziming.auth.server.controller;

import cn.zhangziming.auth.common.model.Result;
import cn.zhangziming.auth.security.context.UserContext;
import cn.zhangziming.auth.server.dto.LoginRequest;
import cn.zhangziming.auth.server.dto.LoginResponse;
import cn.zhangziming.auth.server.service.IAuthService;
import cn.zhangziming.auth.server.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return Result.success(response);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String authorization) {
        // 提取Token (去掉"Bearer "前缀)
        String token = authorization.substring(7);
        authService.logout(token);
        return Result.success();
    }

    /**
     * 刷新Token
     */
    @PostMapping("/refresh")
    public Result<String> refreshToken(@RequestParam String refreshToken) {
        String newAccessToken = authService.refreshToken(refreshToken);
        return Result.success(newAccessToken);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public Result<UserVO> getCurrentUserInfo() {
        UserVO userInfo = authService.getCurrentUserInfo();
        return Result.success(userInfo);
    }
}

