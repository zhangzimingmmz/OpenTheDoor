package cn.zhangziming.auth.server.entity;

import cn.zhangziming.auth.mybatis.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统用户实体
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码(BCrypt加密)
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 性别
     * 0-未知 1-男 2-女
     */
    private Integer gender;

    /**
     * 状态
     * 0-禁用 1-正常 2-锁定
     */
    private Integer status;

    /**
     * 用户类型
     * 1-普通用户 2-管理员 3-超级管理员
     */
    private Integer userType;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 密码修改时间
     */
    private LocalDateTime passwordUpdateTime;
}

