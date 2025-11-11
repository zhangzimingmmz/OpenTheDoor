package cn.zhangziming.auth.server.entity;

import cn.zhangziming.auth.mybatis.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统API接口实体
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_api")
public class SysApi extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * API名称
     */
    private String apiName;

    /**
     * API编码
     */
    private String apiCode;

    /**
     * API路径
     */
    private String apiPath;

    /**
     * 请求方法
     * GET/POST/PUT/DELETE
     */
    private String apiMethod;

    /**
     * API分类
     */
    private String apiCategory;

    /**
     * 是否需要认证
     * 0-否 1-是
     */
    private Integer requireAuth;

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

