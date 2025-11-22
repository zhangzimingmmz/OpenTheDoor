package cn.zhangziming.auth.server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户数据传输对象
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID（更新时需要）
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$", message = "用户名只能包含字母、数字、下划线，长度3-20")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {CreateGroup.class})
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
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
     * 验证分组：创建
     */
    public interface CreateGroup {
    }

    /**
     * 验证分组：更新
     */
    public interface UpdateGroup {
    }
}

