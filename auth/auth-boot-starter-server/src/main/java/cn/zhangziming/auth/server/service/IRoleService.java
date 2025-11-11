package cn.zhangziming.auth.server.service;

import cn.zhangziming.auth.common.model.PageRequest;
import cn.zhangziming.auth.common.model.PageResult;
import cn.zhangziming.auth.server.dto.RoleDTO;
import cn.zhangziming.auth.server.vo.RoleVO;

import java.util.List;

/**
 * 角色服务接口
 *
 * @author zhangziming
 * @since 2024-10-29
 */
public interface IRoleService {

    /**
     * 创建角色
     *
     * @param roleDTO 角色DTO
     * @return 角色ID
     */
    Long saveRole(RoleDTO roleDTO);

    /**
     * 更新角色
     *
     * @param roleDTO 角色DTO
     */
    void updateRole(RoleDTO roleDTO);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     */
    void deleteRole(Long roleId);

    /**
     * 根据ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色VO
     */
    RoleVO getRoleById(Long roleId);

    /**
     * 分页查询角色列表
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    PageResult<RoleVO> listRoles(PageRequest pageRequest);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<RoleVO> listAllRoles();

    /**
     * 分配权限给角色
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表
     */
    void assignPermissions(Long roleId, List<Long> permissionIds);

    /**
     * 查询角色的权限列表
     *
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> getRolePermissions(Long roleId);

    /**
     * 查询用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<RoleVO> getUserRoles(Long userId);

    /**
     * 分配角色给用户
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    void assignRolesToUser(Long userId, List<Long> roleIds);
}

