package cn.zhangziming.auth.server.service.impl;

import cn.zhangziming.auth.common.constant.ErrorCode;
import cn.zhangziming.auth.common.exception.BusinessException;
import cn.zhangziming.auth.common.model.PageRequest;
import cn.zhangziming.auth.common.model.PageResult;
import cn.zhangziming.auth.mybatis.util.PageUtil;
import cn.zhangziming.auth.security.util.SecurityUtil;
import cn.zhangziming.auth.server.dto.UserDTO;
import cn.zhangziming.auth.server.entity.SysUser;
import cn.zhangziming.auth.server.mapper.SysUserMapper;
import cn.zhangziming.auth.server.service.IPermissionService;
import cn.zhangziming.auth.server.service.IRoleService;
import cn.zhangziming.auth.server.service.IUserService;
import cn.zhangziming.auth.server.vo.PermissionVO;
import cn.zhangziming.auth.server.vo.RoleVO;
import cn.zhangziming.auth.server.vo.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final SysUserMapper userMapper;
    private final IRoleService roleService;
    private final IPermissionService permissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveUser(UserDTO userDTO) {
        log.info("创建用户: username={}", userDTO.getUsername());

        // 1. 检查用户名是否存在
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, userDTO.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.USERNAME_EXISTS, "用户名已存在");
        }

        // 2. 检查邮箱是否存在
        if (userDTO.getEmail() != null) {
            wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getEmail, userDTO.getEmail());
            if (userMapper.selectCount(wrapper) > 0) {
                throw new BusinessException(ErrorCode.EMAIL_EXISTS, "邮箱已存在");
            }
        }

        // 3. 检查手机号是否存在
        if (userDTO.getPhone() != null) {
            wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getPhone, userDTO.getPhone());
            if (userMapper.selectCount(wrapper) > 0) {
                throw new BusinessException(ErrorCode.PHONE_EXISTS, "手机号已存在");
            }
        }

        // 4. 创建用户
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userDTO, user);
        
        // 加密密码
        user.setPassword(SecurityUtil.encryptPassword(userDTO.getPassword()));
        user.setPasswordUpdateTime(LocalDateTime.now());
        
        // 设置默认值
        if (user.getStatus() == null) {
            user.setStatus(1); // 默认正常
        }
        if (user.getUserType() == null) {
            user.setUserType(1); // 默认普通用户
        }

        userMapper.insert(user);

        log.info("创建用户成功: userId={}, username={}", user.getId(), user.getUsername());
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserDTO userDTO) {
        log.info("更新用户: userId={}", userDTO.getId());

        // 1. 检查用户是否存在
        SysUser existUser = userMapper.selectById(userDTO.getId());
        if (existUser == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        // 2. 检查用户名是否重复（排除自己）
        if (!existUser.getUsername().equals(userDTO.getUsername())) {
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getUsername, userDTO.getUsername());
            wrapper.ne(SysUser::getId, userDTO.getId());
            if (userMapper.selectCount(wrapper) > 0) {
                throw new BusinessException(ErrorCode.USERNAME_EXISTS, "用户名已存在");
            }
        }

        // 3. 更新用户
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userDTO, user);
        
        // 不更新密码（密码单独接口修改）
        user.setPassword(null);
        
        userMapper.updateById(user);

        log.info("更新用户成功: userId={}", user.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        log.info("删除用户: userId={}", userId);

        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        // 逻辑删除
        userMapper.deleteById(userId);

        log.info("删除用户成功: userId={}", userId);
    }

    @Override
    public UserVO getUserById(Long userId) {
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }
        return convertToVO(user);
    }

    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public PageResult<UserVO> listUsers(PageRequest pageRequest) {
        IPage<SysUser> page = userMapper.selectPage(
                PageUtil.toPage(pageRequest),
                new LambdaQueryWrapper<SysUser>().orderByDesc(SysUser::getCreateTime)
        );

        List<UserVO> voList = page.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.build(page.getTotal(), (int) page.getCurrent(), (int) page.getSize(), voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        log.info("修改密码: userId={}", userId);

        // 1. 查询用户
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        // 2. 验证旧密码
        if (!SecurityUtil.matchPassword(oldPassword, user.getPassword())) {
            throw new BusinessException(ErrorCode.OLD_PASSWORD_ERROR, "原密码错误");
        }

        // 3. 更新密码
        user.setPassword(SecurityUtil.encryptPassword(newPassword));
        user.setPasswordUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);

        log.info("修改密码成功: userId={}", userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long userId, String newPassword) {
        log.info("重置密码: userId={}", userId);

        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        user.setPassword(SecurityUtil.encryptPassword(newPassword));
        user.setPasswordUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);

        log.info("重置密码成功: userId={}", userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long userId, Integer status) {
        log.info("修改用户状态: userId={}, status={}", userId, status);

        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        user.setStatus(status);
        userMapper.updateById(user);

        log.info("修改用户状态成功: userId={}", userId);
    }

    /**
     * 转换为VO
     */
    private UserVO convertToVO(SysUser user) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        vo.setUserId(user.getId());
        
        // 加载用户角色和权限
        List<RoleVO> roles = roleService.getUserRoles(user.getId());
        List<PermissionVO> permissions = permissionService.getUserPermissions(user.getId());
        
        vo.setRoles(roles);
        vo.setPermissions(permissions);
        
        // 设置角色编码和权限编码集合
        vo.setRoleCodes(roles.stream()
                .map(role -> role.getRoleCode())
                .collect(Collectors.toSet()));
        vo.setPermissionCodes(permissions.stream()
                .map(perm -> perm.getPermissionCode())
                .collect(Collectors.toSet()));
        
        return vo;
    }
}

