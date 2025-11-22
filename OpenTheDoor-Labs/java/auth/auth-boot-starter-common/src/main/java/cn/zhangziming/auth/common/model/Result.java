package cn.zhangziming.auth.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应体
 * 
 * <p>所有接口统一返回此格式
 * 
 * @author zhangziming
 * @since 1.0.0
 * @param <T> 数据类型
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 状态码
     */
    private Integer code;
    
    /**
     * 提示信息
     */
    private String message;
    
    /**
     * 数据
     */
    private T data;
    
    /**
     * 时间戳
     */
    private Long timestamp;
    
    /**
     * 链路追踪ID
     */
    private String traceId;
    
    /**
     * 私有构造函数
     */
    private Result() {
        this.timestamp = System.currentTimeMillis();
    }
    
    /**
     * 成功响应（无数据）
     */
    public static <T> Result<T> success() {
        return success(null);
    }
    
    /**
     * 成功响应（有数据）
     * 
     * @param data 数据
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }
    
    /**
     * 成功响应（自定义消息）
     * 
     * @param message 消息
     * @param data 数据
     * @return Result对象
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    
    /**
     * 失败响应
     * 
     * @param message 错误信息
     * @return Result对象
     */
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }
    
    /**
     * 失败响应（指定状态码）
     * 
     * @param code 状态码
     * @param message 错误信息
     * @return Result对象
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    /**
     * 失败响应（指定状态码和数据）
     * 
     * @param code 状态码
     * @param message 错误信息
     * @param data 数据
     * @return Result对象
     */
    public static <T> Result<T> error(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    
    /**
     * 判断是否成功
     * 
     * @return true-成功 false-失败
     */
    public boolean isSuccess() {
        return this.code != null && this.code == 200;
    }
}

