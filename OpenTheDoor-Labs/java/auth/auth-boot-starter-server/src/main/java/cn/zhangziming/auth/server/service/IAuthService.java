package cn.zhangziming.auth.server.service;

import cn.zhangziming.auth.server.dto.LoginRequest;
import cn.zhangziming.auth.server.dto.LoginResponse;
import cn.zhangziming.auth.server.vo.UserVO;

/**
 * 认证服务接口
 *
 * @author zhangziming
 * @since 2024-10-29
 */
public interface IAuthService {

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录响应(包含token和用户信息)
     */
    LoginResponse login(LoginRequest request);

    /**
     * 用户登出
     *
     * @param token Token
     */
    void logout(String token);

    /**
     * 刷新Token
     *
     * @param refreshToken 刷新令牌
     * @return 新的访问令牌
     */
    String refreshToken(String refreshToken);

    /**
     * 验证Token
     *
     * @param token Token
     * @return true-有效 false-无效
     */
    boolean validateToken(String token);

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    UserVO getCurrentUserInfo();
}

