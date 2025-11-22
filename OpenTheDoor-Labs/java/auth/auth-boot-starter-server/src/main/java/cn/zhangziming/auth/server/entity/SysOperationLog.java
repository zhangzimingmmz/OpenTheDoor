package cn.zhangziming.auth.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志实体
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@TableName("sys_operation_log")
public class SysOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 模块名称
     */
    private String module;

    /**
     * 操作类型
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 响应结果
     */
    private String responseResult;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 操作地点
     */
    private String location;

    /**
     * 状态
     * 0-失败 1-成功
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 执行时间(毫秒)
     */
    private Integer executeTime;

    /**
     * 操作时间
     */
    private LocalDateTime createTime;
}

