package cn.zhangziming.auth.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志实体
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@TableName("sys_login_log")
public class SysLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 登录类型
     * 1-账号密码 2-手机验证码 3-第三方
     */
    private Integer loginType;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 设备类型
     */
    private String device;

    /**
     * 登录状态
     * 0-失败 1-成功
     */
    private Integer status;

    /**
     * 提示消息
     */
    private String message;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
}

