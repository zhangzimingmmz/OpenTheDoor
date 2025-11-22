package cn.zhangziming.auth.server.service.impl;

import cn.zhangziming.auth.common.constant.ErrorCode;
import cn.zhangziming.auth.common.exception.BusinessException;
import cn.zhangziming.auth.redis.constant.CacheConstant;
import cn.zhangziming.auth.redis.service.RedisService;
import cn.zhangziming.auth.security.context.UserContext;
import cn.zhangziming.auth.security.context.UserInfo;
import cn.zhangziming.auth.security.jwt.JwtProperties;
import cn.zhangziming.auth.security.jwt.JwtUtil;
import cn.zhangziming.auth.security.util.SecurityUtil;
import cn.zhangziming.auth.server.dto.LoginRequest;
import cn.zhangziming.auth.server.dto.LoginResponse;
import cn.zhangziming.auth.server.entity.SysUser;
import cn.zhangziming.auth.server.mapper.SysUserMapper;
import cn.zhangziming.auth.server.service.IAuthService;
import cn.zhangziming.auth.server.vo.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final SysUserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final RedisService redisService;

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("用户登录: username={}, tenantId={}", request.getUsername(), request.getTenantId());

        // 1. 查询用户
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, request.getUsername());
        if (request.getTenantId() != null) {
            queryWrapper.eq(SysUser::getTenantId, request.getTenantId());
        }
        SysUser user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        // 2. 验证密码
        if (!SecurityUtil.matchPassword(request.getPassword(), user.getPassword())) {
            log.warn("密码错误: username={}", request.getUsername());
            throw new BusinessException(ErrorCode.PASSWORD_ERROR, "密码错误");
        }

        // 3. 检查用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException(ErrorCode.USER_DISABLED, "用户已被禁用");
        }
        if (user.getStatus() == 2) {
            throw new BusinessException(ErrorCode.USER_LOCKED, "用户已被锁定");
        }

        // 4. 生成Token
        String accessToken = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getTenantId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername(), user.getTenantId());

        // 5. 存储Token到Redis
        String tokenKey = CacheConstant.TOKEN_CACHE_PREFIX + accessToken;
        redisService.set(tokenKey, user.getId(), jwtProperties.getExpireTime());

        // 6. 构建响应
        UserVO userVO = buildUserVO(user);
        
        log.info("用户登录成功: userId={}, username={}", user.getId(), user.getUsername());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtProperties.getExpireTime())
                .userInfo(userVO)
                .build();
    }

    @Override
    public void logout(String token) {
        log.info("用户登出");
        
        // 从Redis删除Token
        String tokenKey = CacheConstant.TOKEN_CACHE_PREFIX + token;
        redisService.delete(tokenKey);
        
        log.info("用户登出成功");
    }

    @Override
    public String refreshToken(String refreshToken) {
        log.info("刷新Token");

        // 1. 验证RefreshToken
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID, "Token无效");
        }

        // 2. 提取用户信息
        Long userId = jwtUtil.extractUserId(refreshToken);
        String username = jwtUtil.extractUsername(refreshToken);
        String tenantId = jwtUtil.extractTenantId(refreshToken);

        // 3. 生成新的AccessToken
        String newAccessToken = jwtUtil.generateToken(userId, username, tenantId);

        // 4. 存储新Token到Redis
        String tokenKey = CacheConstant.TOKEN_CACHE_PREFIX + newAccessToken;
        redisService.set(tokenKey, userId, jwtProperties.getExpireTime());

        log.info("Token刷新成功: userId={}", userId);

        return newAccessToken;
    }

    @Override
    public boolean validateToken(String token) {
        // 验证JWT格式和签名
        if (!jwtUtil.validateToken(token)) {
            return false;
        }

        // 检查Redis中是否存在
        String tokenKey = CacheConstant.TOKEN_CACHE_PREFIX + token;
        return redisService.hasKey(tokenKey);
    }

    @Override
    public UserVO getCurrentUserInfo() {
        // 从UserContext获取当前用户信息
        UserInfo currentUser = UserContext.getCurrentUser();
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "未登录");
        }

        // 查询完整用户信息
        SysUser user = userMapper.selectById(currentUser.getUserId());
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        // 构建用户VO
        return buildUserVO(user);
    }

    /**
     * 构建用户VO
     */
    private UserVO buildUserVO(SysUser user) {
        UserVO userVO = new UserVO();
        userVO.setUserId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setNickname(user.getNickname());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());
        userVO.setAvatar(user.getAvatar());
        userVO.setGender(user.getGender());
        userVO.setStatus(user.getStatus());
        userVO.setUserType(user.getUserType());
        userVO.setTenantId(user.getTenantId());
        userVO.setLastLoginTime(user.getLastLoginTime());
        userVO.setCreateTime(user.getCreateTime());
        
        // TODO: 加载用户角色和权限
        
        return userVO;
    }
}

