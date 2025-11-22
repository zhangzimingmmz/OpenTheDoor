package cn.zhangziming.auth.server.service;

import cn.zhangziming.auth.common.model.PageRequest;
import cn.zhangziming.auth.common.model.PageResult;
import cn.zhangziming.auth.server.dto.UserDTO;
import cn.zhangziming.auth.server.entity.SysUser;
import cn.zhangziming.auth.server.vo.UserVO;

/**
 * 用户服务接口
 *
 * @author zhangziming
 * @since 2024-10-29
 */
public interface IUserService {

    /**
     * 创建用户
     *
     * @param userDTO 用户DTO
     * @return 用户ID
     */
    Long saveUser(UserDTO userDTO);

    /**
     * 更新用户
     *
     * @param userDTO 用户DTO
     */
    void updateUser(UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    void deleteUser(Long userId);

    /**
     * 根据ID查询用户
     *
     * @param userId 用户ID
     * @return 用户VO
     */
    UserVO getUserById(Long userId);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    SysUser getUserByUsername(String username);

    /**
     * 分页查询用户列表
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    PageResult<UserVO> listUsers(PageRequest pageRequest);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 重置密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     */
    void resetPassword(Long userId, String newPassword);

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     */
    void updateStatus(Long userId, Integer status);
}

