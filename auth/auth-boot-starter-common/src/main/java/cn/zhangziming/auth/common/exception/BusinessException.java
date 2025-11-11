package cn.zhangziming.auth.common.exception;

/**
 * 业务异常
 * 
 * <p>可预期的业务逻辑异常，需要给用户友好提示
 * <p>示例：用户名已存在、密码错误、余额不足等
 * 
 * @author zhangziming
 * @since 1.0.0
 */
public class BusinessException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 默认错误码
     */
    private static final Integer DEFAULT_CODE = 400;
    
    public BusinessException(String message) {
        super(DEFAULT_CODE, message);
    }
    
    public BusinessException(Integer code, String message) {
        super(code, message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(DEFAULT_CODE, message, cause);
    }
    
    public BusinessException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

