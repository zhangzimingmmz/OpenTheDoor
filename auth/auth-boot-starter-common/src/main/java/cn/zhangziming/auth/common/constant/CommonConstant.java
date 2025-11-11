package cn.zhangziming.auth.common.constant;

/**
 * 公共常量
 * 
 * @author zhangziming
 * @since 1.0.0
 */
public interface CommonConstant {
    
    /**
     * 默认字符集
     */
    String DEFAULT_CHARSET = "UTF-8";
    
    /**
     * 链路追踪ID的HTTP头名称
     */
    String TRACE_ID_HEADER = "X-Trace-Id";
    
    /**
     * 认证头名称
     */
    String AUTHORIZATION_HEADER = "Authorization";
    
    /**
     * Token前缀
     */
    String TOKEN_PREFIX = "Bearer ";
    
    /**
     * 成功状态码
     */
    Integer SUCCESS_CODE = 200;
    
    /**
     * 失败状态码
     */
    Integer ERROR_CODE = 500;
    
    /**
     * 成功消息
     */
    String SUCCESS_MESSAGE = "success";
    
    /**
     * 失败消息
     */
    String ERROR_MESSAGE = "操作失败";
    
    /**
     * 系统异常消息
     */
    String SYSTEM_ERROR_MESSAGE = "系统异常，请稍后重试";
    
    /**
     * 默认分页大小
     */
    Integer DEFAULT_PAGE_SIZE = 10;
    
    /**
     * 最大分页大小
     */
    Integer MAX_PAGE_SIZE = 100;
    
    /**
     * 逻辑删除 - 未删除
     */
    Integer NOT_DELETED = 0;
    
    /**
     * 逻辑删除 - 已删除
     */
    Integer DELETED = 1;
    
    /**
     * 状态 - 禁用
     */
    Integer STATUS_DISABLED = 0;
    
    /**
     * 状态 - 正常
     */
    Integer STATUS_NORMAL = 1;
    
    /**
     * 是 - 1
     */
    Integer YES = 1;
    
    /**
     * 否 - 0
     */
    Integer NO = 0;
}

