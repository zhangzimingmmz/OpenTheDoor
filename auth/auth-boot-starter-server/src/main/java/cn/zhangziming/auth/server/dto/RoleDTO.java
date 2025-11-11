package cn.zhangziming.auth.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色数据传输对象
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID（更新时需要）
     */
    private Long id;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 状态
     * 0-禁用 1-正常
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 权限ID列表
     */
    private List<Long> permissionIds;
}

