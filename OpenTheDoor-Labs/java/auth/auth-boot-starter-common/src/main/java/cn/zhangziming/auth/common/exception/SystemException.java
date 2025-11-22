package cn.zhangziming.auth.common.exception;

/**
 * 系统异常
 * 
 * <p>不可预期的系统级异常，需要记录日志并给用户通用提示
 * <p>示例：数据库连接失败、第三方服务调用失败、空指针异常等
 * 
 * @author zhangziming
 * @since 1.0.0
 */
public class SystemException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 默认错误码
     */
    private static final Integer DEFAULT_CODE = 500;
    
    public SystemException(String message) {
        super(DEFAULT_CODE, message);
    }
    
    public SystemException(Integer code, String message) {
        super(code, message);
    }
    
    public SystemException(String message, Throwable cause) {
        super(DEFAULT_CODE, message, cause);
    }
    
    public SystemException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

