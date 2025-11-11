package cn.zhangziming.auth.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单数据传输对象
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
public class MenuDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID（更新时需要）
     */
    private Long id;

    /**
     * 菜单编码
     */
    @NotBlank(message = "菜单编码不能为空")
    private String menuCode;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单类型
     * 1-目录 2-菜单 3-按钮
     */
    private Integer menuType;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 是否可见
     * 0-隐藏 1-显示
     */
    private Integer visible;

    /**
     * 状态
     * 0-禁用 1-正常
     */
    private Integer status;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 备注
     */
    private String remark;
}

