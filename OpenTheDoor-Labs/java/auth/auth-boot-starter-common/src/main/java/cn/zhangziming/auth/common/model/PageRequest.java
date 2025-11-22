package cn.zhangziming.auth.common.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求参数
 * 
 * <p>统一的分页请求封装
 * 
 * @author zhangziming
 * @since 1.0.0
 */
@Data
public class PageRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 当前页码
     */
    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum = 1;
    
    /**
     * 每页大小
     */
    @Min(value = 1, message = "每页大小最小为1")
    @Max(value = 100, message = "每页大小最大为100")
    private Integer pageSize = 10;
    
    /**
     * 排序字段
     */
    private String orderBy;
    
    /**
     * 排序方式 (asc/desc)
     */
    private String sortOrder = "desc";
    
    /**
     * 获取偏移量
     * 
     * @return 偏移量
     */
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}

