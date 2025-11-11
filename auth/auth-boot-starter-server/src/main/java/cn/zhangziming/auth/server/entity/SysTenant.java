package cn.zhangziming.auth.server.entity;

import cn.zhangziming.auth.mybatis.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统租户实体
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tenant")
public class SysTenant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 账号数量限制
     * -1表示不限制
     */
    private Integer accountLimit;

    /**
     * 状态
     * 0-禁用 1-正常 2-过期
     */
    private Integer status;

    /**
     * Logo URL
     */
    private String logo;

    /**
     * 域名
     */
    private String domain;

    /**
     * 备注
     */
    private String remark;
}

