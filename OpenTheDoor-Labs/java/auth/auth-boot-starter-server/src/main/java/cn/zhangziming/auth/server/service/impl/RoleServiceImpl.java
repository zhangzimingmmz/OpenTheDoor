package cn.zhangziming.auth.server.service.impl;

import cn.zhangziming.auth.common.constant.ErrorCode;
import cn.zhangziming.auth.common.exception.BusinessException;
import cn.zhangziming.auth.common.model.PageRequest;
import cn.zhangziming.auth.common.model.PageResult;
import cn.zhangziming.auth.mybatis.util.PageUtil;
import cn.zhangziming.auth.server.dto.RoleDTO;
import cn.zhangziming.auth.server.entity.SysPermission;
import cn.zhangziming.auth.server.entity.SysRole;
import cn.zhangziming.auth.server.entity.SysRolePermission;
import cn.zhangziming.auth.server.entity.SysUser;
import cn.zhangziming.auth.server.entity.SysUserRole;
import cn.zhangziming.auth.server.mapper.SysPermissionMapper;
import cn.zhangziming.auth.server.mapper.SysRoleMapper;
import cn.zhangziming.auth.server.mapper.SysRolePermissionMapper;
import cn.zhangziming.auth.server.mapper.SysUserMapper;
import cn.zhangziming.auth.server.mapper.SysUserRoleMapper;
import cn.zhangziming.auth.server.service.IRoleService;
import cn.zhangziming.auth.server.vo.PermissionVO;
import cn.zhangziming.auth.server.vo.RoleVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final SysRoleMapper roleMapper;
    private final SysPermissionMapper permissionMapper;
    private final SysRolePermissionMapper rolePermissionMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final SysUserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveRole(RoleDTO roleDTO) {
        log.info("创建角色: roleCode={}", roleDTO.getRoleCode());

        // 1. 检查角色编码是否存在
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleCode, roleDTO.getRoleCode());
        if (roleMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.ROLE_CODE_EXISTS, "角色编码已存在");
        }

        // 2. 创建角色
        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleDTO, role);
        
        // 设置默认值
        if (role.getStatus() == null) {
            role.setStatus(1); // 默认正常
        }

        roleMapper.insert(role);

        // 3. 分配权限
        if (!CollectionUtils.isEmpty(roleDTO.getPermissionIds())) {
            assignPermissions(role.getId(), roleDTO.getPermissionIds());
        }

        log.info("创建角色成功: roleId={}, roleCode={}", role.getId(), role.getRoleCode());
        return role.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleDTO roleDTO) {
        log.info("更新角色: roleId={}", roleDTO.getId());

        // 1. 检查角色是否存在
        SysRole existRole = roleMapper.selectById(roleDTO.getId());
        if (existRole == null) {
            throw new BusinessException(ErrorCode.ROLE_NOT_FOUND, "角色不存在");
        }

        // 2. 检查角色编码是否重复（排除自己）
        if (!existRole.getRoleCode().equals(roleDTO.getRoleCode())) {
            LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRole::getRoleCode, roleDTO.getRoleCode());
            wrapper.ne(SysRole::getId, roleDTO.getId());
            if (roleMapper.selectCount(wrapper) > 0) {
                throw new BusinessException(ErrorCode.ROLE_CODE_EXISTS, "角色编码已存在");
            }
        }

        // 3. 更新角色
        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleDTO, role);
        roleMapper.updateById(role);

        // 4. 更新权限
        if (roleDTO.getPermissionIds() != null) {
            assignPermissions(role.getId(), roleDTO.getPermissionIds());
        }

        log.info("更新角色成功: roleId={}", role.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long roleId) {
        log.info("删除角色: roleId={}", roleId);

        SysRole role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BusinessException(ErrorCode.ROLE_NOT_FOUND, "角色不存在");
        }

        // 1. 检查是否有用户使用该角色
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getRoleId, roleId);
        if (userRoleMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.ROLE_IN_USE, "该角色已分配给用户，无法删除");
        }

        // 2. 删除角色权限关联
        LambdaQueryWrapper<SysRolePermission> rpWrapper = new LambdaQueryWrapper<>();
        rpWrapper.eq(SysRolePermission::getRoleId, roleId);
        rolePermissionMapper.delete(rpWrapper);

        // 3. 删除角色（逻辑删除）
        roleMapper.deleteById(roleId);

        log.info("删除角色成功: roleId={}", roleId);
    }

    @Override
    public RoleVO getRoleById(Long roleId) {
        SysRole role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BusinessException(ErrorCode.ROLE_NOT_FOUND, "角色不存在");
        }
        return convertToVO(role, true);
    }

    @Override
    public PageResult<RoleVO> listRoles(PageRequest pageRequest) {
        IPage<SysRole> page = roleMapper.selectPage(
                PageUtil.toPage(pageRequest),
                new LambdaQueryWrapper<SysRole>().orderByAsc(SysRole::getRoleLevel)
        );

        List<RoleVO> voList = page.getRecords().stream()
                .map(role -> convertToVO(role, false))
                .collect(Collectors.toList());

        return PageResult.build(page.getTotal(), (int) page.getCurrent(), (int) page.getSize(), voList);
    }

    @Override
    public List<RoleVO> listAllRoles() {
        List<SysRole> roles = roleMapper.selectList(
                new LambdaQueryWrapper<SysRole>()
                        .eq(SysRole::getStatus, 1)
                        .orderByAsc(SysRole::getRoleLevel)
        );
        return roles.stream()
                .map(role -> convertToVO(role, false))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignPermissions(Long roleId, List<Long> permissionIds) {
        log.info("分配权限给角色: roleId={}, permissionIds={}", roleId, permissionIds);

        // 1. 检查角色是否存在
        SysRole role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BusinessException(ErrorCode.ROLE_NOT_FOUND, "角色不存在");
        }

        // 2. 删除原有权限
        LambdaQueryWrapper<SysRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRolePermission::getRoleId, roleId);
        rolePermissionMapper.delete(wrapper);

        // 3. 添加新权限
        if (!CollectionUtils.isEmpty(permissionIds)) {
            for (Long permissionId : permissionIds) {
                SysRolePermission rolePermission = new SysRolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermission.setTenantId(role.getTenantId()); // 设置租户ID
                rolePermissionMapper.insert(rolePermission);
            }
        }

        log.info("分配权限成功: roleId={}", roleId);
    }

    @Override
    public List<Long> getRolePermissions(Long roleId) {
        LambdaQueryWrapper<SysRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRolePermission::getRoleId, roleId);
        List<SysRolePermission> rolePermissions = rolePermissionMapper.selectList(wrapper);
        
        return rolePermissions.stream()
                .map(SysRolePermission::getPermissionId)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleVO> getUserRoles(Long userId) {
        // 1. 查询用户角色关联
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> userRoles = userRoleMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(userRoles)) {
            return new ArrayList<>();
        }

        // 2. 查询角色信息
        List<Long> roleIds = userRoles.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());

        List<SysRole> roles = roleMapper.selectBatchIds(roleIds);
        return roles.stream()
                .map(role -> convertToVO(role, false))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRolesToUser(Long userId, List<Long> roleIds) {
        log.info("分配角色给用户: userId={}, roleIds={}", userId, roleIds);

        // 1. 删除原有角色
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        userRoleMapper.delete(wrapper);

        // 2. 添加新角色
        if (!CollectionUtils.isEmpty(roleIds)) {
            // 获取用户租户ID
            SysUser user = userMapper.selectById(userId);
            String tenantId = user != null ? user.getTenantId() : "DEFAULT";
            
            for (Long roleId : roleIds) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setTenantId(tenantId); // 设置租户ID
                userRoleMapper.insert(userRole);
            }
        }

        log.info("分配角色成功: userId={}", userId);
    }

    /**
     * 转换为VO
     *
     * @param role             角色实体
     * @param includePermissions 是否包含权限列表
     * @return 角色VO
     */
    private RoleVO convertToVO(SysRole role, boolean includePermissions) {
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(role, vo);
        vo.setRoleId(role.getId());

        // 加载权限列表
        if (includePermissions) {
            List<Long> permissionIds = getRolePermissions(role.getId());
            if (!CollectionUtils.isEmpty(permissionIds)) {
                List<SysPermission> permissions = permissionMapper.selectBatchIds(permissionIds);
                List<PermissionVO> permissionVOs = permissions.stream()
                        .map(this::convertPermissionToVO)
                        .collect(Collectors.toList());
                vo.setPermissions(permissionVOs);
            }
        }

        return vo;
    }

    /**
     * 转换权限为VO
     */
    private PermissionVO convertPermissionToVO(SysPermission permission) {
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(permission, vo);
        vo.setPermissionId(permission.getId());
        return vo;
    }
}

