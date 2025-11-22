package cn.zhangziming.auth.common.exception;

import lombok.Getter;

/**
 * 基础异常类
 * 
 * <p>所有自定义异常的父类
 * 
 * @author zhangziming
 * @since 1.0.0
 */
@Getter
public class BaseException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     */
    private Integer code;
    
    /**
     * 错误信息
     */
    private String message;
    
    public BaseException(String message) {
        super(message);
        this.message = message;
    }
    
    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
    
    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}

