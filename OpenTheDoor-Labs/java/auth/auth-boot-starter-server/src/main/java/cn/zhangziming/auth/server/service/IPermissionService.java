package cn.zhangziming.auth.server.service;

import cn.zhangziming.auth.server.vo.PermissionVO;

import java.util.List;
import java.util.Set;

/**
 * 权限服务接口
 *
 * @author zhangziming
 * @since 2024-10-29
 */
public interface IPermissionService {

    /**
     * 查询所有权限
     *
     * @return 权限列表
     */
    List<PermissionVO> listAllPermissions();

    /**
     * 根据权限类型查询权限
     *
     * @param permissionType 权限类型 1-菜单 2-按钮 3-API
     * @return 权限列表
     */
    List<PermissionVO> listPermissionsByType(Integer permissionType);

    /**
     * 查询用户的权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<PermissionVO> getUserPermissions(Long userId);

    /**
     * 查询用户的权限编码集合
     *
     * @param userId 用户ID
     * @return 权限编码集合
     */
    Set<String> getUserPermissionCodes(Long userId);

    /**
     * 检查用户是否拥有指定权限
     *
     * @param userId         用户ID
     * @param permissionCode 权限编码
     * @return 是否拥有权限
     */
    boolean hasPermission(Long userId, String permissionCode);

    /**
     * 检查用户是否拥有所有指定权限
     *
     * @param userId          用户ID
     * @param permissionCodes 权限编码集合
     * @return 是否拥有所有权限
     */
    boolean hasAllPermissions(Long userId, Set<String> permissionCodes);

    /**
     * 检查用户是否拥有任一指定权限
     *
     * @param userId          用户ID
     * @param permissionCodes 权限编码集合
     * @return 是否拥有任一权限
     */
    boolean hasAnyPermission(Long userId, Set<String> permissionCodes);
}

