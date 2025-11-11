package cn.zhangziming.auth.server.entity;

import cn.zhangziming.auth.mybatis.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统权限实体
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
public class SysPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 权限编码
     * 如: user:read, user:write
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
     * 父权限ID
     */
    private Long parentId;

    /**
     * 关联资源ID
     */
    private Long resourceId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 状态
     * 0-禁用 1-正常
     */
    private Integer status;

    /**
     * 排序
     */
    @TableField("sort_order")
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;
}

