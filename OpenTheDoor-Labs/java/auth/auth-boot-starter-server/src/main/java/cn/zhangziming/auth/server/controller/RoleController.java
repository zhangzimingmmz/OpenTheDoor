package cn.zhangziming.auth.server.controller;

import cn.zhangziming.auth.common.constant.ErrorCode;
import cn.zhangziming.auth.common.exception.BusinessException;
import cn.zhangziming.auth.common.model.PageRequest;
import cn.zhangziming.auth.common.model.PageResult;
import cn.zhangziming.auth.common.model.Result;
import cn.zhangziming.auth.server.dto.AssignPermissionsRequest;
import cn.zhangziming.auth.server.dto.AssignRolesRequest;
import cn.zhangziming.auth.server.dto.RoleDTO;
import cn.zhangziming.auth.server.service.IRoleService;
import cn.zhangziming.auth.server.vo.RoleVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    /**
     * 分页查询角色列表
     */
    @GetMapping("/list")
    public Result<PageResult<RoleVO>> listRoles(PageRequest pageRequest) {
        PageResult<RoleVO> result = roleService.listRoles(pageRequest);
        return Result.success(result);
    }

    /**
     * 查询所有角色
     */
    @GetMapping("/all")
    public Result<List<RoleVO>> listAllRoles() {
        List<RoleVO> roles = roleService.listAllRoles();
        return Result.success(roles);
    }

    /**
     * 查询角色详情
     */
    @GetMapping("/{id}")
    public Result<RoleVO> getRole(@PathVariable Long id) {
        RoleVO role = roleService.getRoleById(id);
        return Result.success(role);
    }

    /**
     * 创建角色
     */
    @PostMapping
    public Result<Long> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        Long roleId = roleService.saveRole(roleDTO);
        return Result.success(roleId);
    }

    /**
     * 更新角色
     */
    @PutMapping("/{id}")
    public Result<Void> updateRole(@PathVariable Long id, 
                                    @Valid @RequestBody RoleDTO roleDTO) {
        roleDTO.setId(id);
        roleService.updateRole(roleDTO);
        return Result.success();
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return Result.success();
    }

    /**
     * 分配权限给角色
     * 
     * @param id 角色ID
     * @param request 包含权限ID列表的请求体
     * @return 操作结果
     */
    @PostMapping("/{id}/assign-permissions")
    public Result<Void> assignPermissions(
            @PathVariable("id") Long id,
            @Valid @RequestBody AssignPermissionsRequest request) {
        log.info("收到分配权限请求: roleId={}, request={}", id, request);
        roleService.assignPermissions(id, request.getPermissionIds());
        return Result.success();
    }

    /**
     * 查询角色的权限列表
     */
    @GetMapping("/{id}/permissions")
    public Result<List<Long>> getRolePermissions(@PathVariable Long id) {
        List<Long> permissionIds = roleService.getRolePermissions(id);
        return Result.success(permissionIds);
    }

    /**
     * 查询用户的角色列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<RoleVO>> getUserRoles(@PathVariable Long userId) {
        List<RoleVO> roles = roleService.getUserRoles(userId);
        return Result.success(roles);
    }

    /**
     * 分配角色给用户
     */
    @PostMapping("/assign-to-user/{userId}")
    public Result<Void> assignRolesToUser(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody AssignRolesRequest request) {
        log.info("收到分配角色请求: userId={}, request={}", userId, request);
        roleService.assignRolesToUser(userId, request.getRoleIds());
        return Result.success();
    }
}

