package cn.zhangziming.auth.server.service.impl;

import cn.zhangziming.auth.server.entity.SysUser;
import cn.zhangziming.auth.server.mapper.SysUserMapper;
import cn.zhangziming.auth.server.service.IPermissionService;
import cn.zhangziming.auth.server.service.IRoleService;
import cn.zhangziming.auth.server.vo.RoleVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Spring Security用户详情服务实现
 * 
 * <p>用于Spring Security认证时加载用户信息和权限
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SysUserMapper userMapper;
    private final IRoleService roleService;
    private final IPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("加载用户信息: username={}", username);

        // 1. 查询用户
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        SysUser user = userMapper.selectOne(wrapper);

        if (user == null) {
            log.warn("用户不存在: username={}", username);
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        // 2. 检查用户状态
        if (user.getStatus() == null || user.getStatus() == 0) {
            log.warn("用户已被禁用: username={}", username);
            throw new UsernameNotFoundException("用户已被禁用: " + username);
        }

        if (user.getStatus() == 2) {
            log.warn("用户已被锁定: username={}", username);
            throw new UsernameNotFoundException("用户已被锁定: " + username);
        }

        // 3. 加载用户权限
        Collection<? extends GrantedAuthority> authorities = loadUserAuthorities(user.getId());

        log.debug("用户加载成功: username={}, authorities={}", username, authorities.size());

        // 4. 构建UserDetails对象
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(user.getStatus() == 2)
                .credentialsExpired(false)
                .disabled(user.getStatus() == 0)
                .build();
    }

    /**
     * 加载用户权限
     * 
     * @param userId 用户ID
     * @return 权限集合
     */
    private Collection<? extends GrantedAuthority> loadUserAuthorities(Long userId) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        try {
            // 1. 加载角色
            List<RoleVO> roles = roleService.getUserRoles(userId);
            if (roles != null && !roles.isEmpty()) {
                // 添加角色权限（以ROLE_前缀标识）
                authorities.addAll(roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()))
                        .collect(Collectors.toList()));
            }

            // 2. 加载权限
            Set<String> permissionCodes = permissionService.getUserPermissionCodes(userId);
            if (permissionCodes != null && !permissionCodes.isEmpty()) {
                // 添加权限
                authorities.addAll(permissionCodes.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
            }

            log.debug("用户权限加载成功: userId={}, roleCount={}, permissionCount={}", 
                    userId, 
                    roles != null ? roles.size() : 0, 
                    permissionCodes != null ? permissionCodes.size() : 0);

        } catch (Exception e) {
            log.error("加载用户权限失败: userId={}", userId, e);
        }

        // 如果没有任何权限，至少返回一个基础权限
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authorities;
    }

    /**
     * 根据用户ID加载用户详情
     * 
     * @param userId 用户ID
     * @return 用户详情
     */
    public UserDetails loadUserById(Long userId) {
        log.debug("根据ID加载用户信息: userId={}", userId);

        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            log.warn("用户不存在: userId={}", userId);
            throw new UsernameNotFoundException("用户不存在: " + userId);
        }

        Collection<? extends GrantedAuthority> authorities = loadUserAuthorities(userId);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(user.getStatus() == 2)
                .credentialsExpired(false)
                .disabled(user.getStatus() == 0)
                .build();
    }
}

