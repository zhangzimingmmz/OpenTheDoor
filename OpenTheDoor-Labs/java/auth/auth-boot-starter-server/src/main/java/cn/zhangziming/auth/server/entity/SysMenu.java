package cn.zhangziming.auth.server.entity;

import cn.zhangziming.auth.mybatis.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统菜单实体
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单编码
     */
    private String menuCode;

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
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    @TableField("sort_order")
    private Integer sortOrder;

    /**
     * 是否可见
     * 0-否 1-是
     */
    private Integer visible;

    /**
     * 状态
     * 0-禁用 1-正常
     */
    private Integer status;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 是否外链
     * 0-否 1-是
     */
    private Integer isExternal;

    /**
     * 是否缓存
     * 0-否 1-是
     */
    private Integer keepAlive;

    /**
     * 备注
     */
    private String remark;
}

