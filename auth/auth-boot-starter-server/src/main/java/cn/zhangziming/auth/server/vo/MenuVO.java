package cn.zhangziming.auth.server.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单视图对象
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
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
    private Integer sort;

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
     * 子菜单列表
     */
    private List<MenuVO> children;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

