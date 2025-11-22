package cn.zhangziming.auth.server.entity;

import cn.zhangziming.auth.mybatis.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色实体
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色级别
     * 数字越小权限越大
     */
    private Integer roleLevel;

    /**
     * 数据范围
     * 1-全部 2-本部门及下级 3-本部门 4-仅本人 5-自定义
     */
    private Integer dataScope;

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
     * 备注
     */
    private String remark;
}

