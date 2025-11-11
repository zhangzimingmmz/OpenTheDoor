package cn.zhangziming.auth.server.service.impl;

import cn.zhangziming.auth.server.entity.SysPermission;
import cn.zhangziming.auth.server.entity.SysRolePermission;
import cn.zhangziming.auth.server.entity.SysUserRole;
import cn.zhangziming.auth.server.mapper.SysPermissionMapper;
import cn.zhangziming.auth.server.mapper.SysRolePermissionMapper;
import cn.zhangziming.auth.server.mapper.SysUserRoleMapper;
import cn.zhangziming.auth.server.service.IPermissionService;
import cn.zhangziming.auth.server.vo.PermissionVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限服务实现
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService {

    private final SysPermissionMapper permissionMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final SysRolePermissionMapper rolePermissionMapper;

    @Override
    public List<PermissionVO> listAllPermissions() {
        List<SysPermission> permissions = permissionMapper.selectList(
                new LambdaQueryWrapper<SysPermission>()
                        .eq(SysPermission::getStatus, 1)
                        .orderByAsc(SysPermission::getSortOrder)
        );
        return permissions.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionVO> listPermissionsByType(Integer permissionType) {
        List<SysPermission> permissions = permissionMapper.selectList(
                new LambdaQueryWrapper<SysPermission>()
                        .eq(SysPermission::getPermissionType, permissionType)
                        .eq(SysPermission::getStatus, 1)
                        .orderByAsc(SysPermission::getSortOrder)
        );
        return permissions.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionVO> getUserPermissions(Long userId) {
        // 1. 查询用户角色
        LambdaQueryWrapper<SysUserRole> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> userRoles = userRoleMapper.selectList(userRoleWrapper);

        if (CollectionUtils.isEmpty(userRoles)) {
            return new ArrayList<>();
        }

        // 2. 查询角色权限
        List<Long> roleIds = userRoles.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());

        LambdaQueryWrapper<SysRolePermission> rolePermWrapper = new LambdaQueryWrapper<>();
        rolePermWrapper.in(SysRolePermission::getRoleId, roleIds);
        List<SysRolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermWrapper);

        if (CollectionUtils.isEmpty(rolePermissions)) {
            return new ArrayList<>();
        }

        // 3. 查询权限详情
        Set<Long> permissionIds = rolePermissions.stream()
                .map(SysRolePermission::getPermissionId)
                .collect(Collectors.toSet());

        List<SysPermission> permissions = permissionMapper.selectBatchIds(permissionIds);
        return permissions.stream()
                .filter(p -> p.getStatus() == 1) // 只返回启用的权限
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getUserPermissionCodes(Long userId) {
        List<PermissionVO> permissions = getUserPermissions(userId);
        return permissions.stream()
                .map(PermissionVO::getPermissionCode)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean hasPermission(Long userId, String permissionCode) {
        Set<String> permissionCodes = getUserPermissionCodes(userId);
        return permissionCodes.contains(permissionCode);
    }

    @Override
    public boolean hasAllPermissions(Long userId, Set<String> permissionCodes) {
        if (CollectionUtils.isEmpty(permissionCodes)) {
            return true;
        }
        Set<String> userPermissions = getUserPermissionCodes(userId);
        return userPermissions.containsAll(permissionCodes);
    }

    @Override
    public boolean hasAnyPermission(Long userId, Set<String> permissionCodes) {
        if (CollectionUtils.isEmpty(permissionCodes)) {
            return true;
        }
        Set<String> userPermissions = getUserPermissionCodes(userId);
        for (String code : permissionCodes) {
            if (userPermissions.contains(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 转换为VO
     */
    private PermissionVO convertToVO(SysPermission permission) {
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(permission, vo);
        vo.setPermissionId(permission.getId());
        return vo;
    }
}

