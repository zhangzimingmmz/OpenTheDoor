package cn.zhangziming.auth.server.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限视图对象
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
public class PermissionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    private Long permissionId;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限类型
     * 1-菜单 2-按钮 3-API
     */
    private Integer permissionType;

    /**
     * 资源路径
     */
    private String resourcePath;

    /**
     * 资源方法
     */
    private String resourceMethod;

    /**
     * 父权限ID
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态
     * 0-禁用 1-正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

