package cn.zhangziming.auth.server.controller;

import cn.zhangziming.auth.common.model.PageRequest;
import cn.zhangziming.auth.common.model.PageResult;
import cn.zhangziming.auth.common.model.Result;
import cn.zhangziming.auth.server.dto.UserDTO;
import cn.zhangziming.auth.server.service.IUserService;
import cn.zhangziming.auth.server.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/list")
    public Result<PageResult<UserVO>> listUsers(PageRequest pageRequest) {
        PageResult<UserVO> result = userService.listUsers(pageRequest);
        return Result.success(result);
    }

    /**
     * 查询用户详情
     */
    @GetMapping("/{id}")
    public Result<UserVO> getUser(@PathVariable Long id) {
        UserVO user = userService.getUserById(id);
        return Result.success(user);
    }

    /**
     * 创建用户
     */
    @PostMapping
    public Result<Long> createUser(@Validated(UserDTO.CreateGroup.class) @RequestBody UserDTO userDTO) {
        Long userId = userService.saveUser(userDTO);
        return Result.success(userId);
    }

    /**
     * 更新用户
     */
    @PutMapping("/{id}")
    public Result<Void> updateUser(@PathVariable Long id, 
                                    @Validated(UserDTO.UpdateGroup.class) @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        userService.updateUser(userDTO);
        return Result.success();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/{id}/password")
    public Result<Void> updatePassword(@PathVariable Long id,
                                        @RequestParam String oldPassword,
                                        @RequestParam String newPassword) {
        userService.updatePassword(id, oldPassword, newPassword);
        return Result.success();
    }

    /**
     * 重置密码
     */
    @PutMapping("/{id}/reset-password")
    public Result<Void> resetPassword(@PathVariable Long id,
                                       @RequestParam String newPassword) {
        userService.resetPassword(id, newPassword);
        return Result.success();
    }

    /**
     * 修改用户状态
     */
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id,
                                      @RequestParam Integer status) {
        userService.updateStatus(id, status);
        return Result.success();
    }
}

