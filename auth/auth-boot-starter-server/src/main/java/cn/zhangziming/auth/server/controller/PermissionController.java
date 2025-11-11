package cn.zhangziming.auth.server.controller;

import cn.zhangziming.auth.common.model.Result;
import cn.zhangziming.auth.server.service.IPermissionService;
import cn.zhangziming.auth.server.vo.PermissionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 权限管理控制器
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final IPermissionService permissionService;

    /**
     * 查询所有权限
     */
    @GetMapping("/all")
    public Result<List<PermissionVO>> listAllPermissions() {
        List<PermissionVO> permissions = permissionService.listAllPermissions();
        return Result.success(permissions);
    }

    /**
     * 根据类型查询权限
     */
    @GetMapping("/type/{type}")
    public Result<List<PermissionVO>> listPermissionsByType(@PathVariable Integer type) {
        List<PermissionVO> permissions = permissionService.listPermissionsByType(type);
        return Result.success(permissions);
    }

    /**
     * 查询用户的权限列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<PermissionVO>> getUserPermissions(@PathVariable Long userId) {
        List<PermissionVO> permissions = permissionService.getUserPermissions(userId);
        return Result.success(permissions);
    }

    /**
     * 查询用户的权限编码集合
     */
    @GetMapping("/user/{userId}/codes")
    public Result<Set<String>> getUserPermissionCodes(@PathVariable Long userId) {
        Set<String> codes = permissionService.getUserPermissionCodes(userId);
        return Result.success(codes);
    }

    /**
     * 检查用户是否拥有指定权限
     */
    @GetMapping("/check/{userId}/{code}")
    public Result<Boolean> hasPermission(@PathVariable Long userId,
                                          @PathVariable String code) {
        boolean has = permissionService.hasPermission(userId, code);
        return Result.success(has);
    }
}

