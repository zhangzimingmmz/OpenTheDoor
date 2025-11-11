package cn.zhangziming.auth.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页响应结果
 * 
 * <p>统一的分页响应封装
 * 
 * @author zhangziming
 * @since 1.0.0
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 当前页码
     */
    private Integer pageNum;
    
    /**
     * 每页大小
     */
    private Integer pageSize;
    
    /**
     * 总页数
     */
    private Integer pages;
    
    /**
     * 数据列表
     */
    private List<T> list;
    
    /**
     * 构造分页结果
     * 
     * @param total 总记录数
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param list 数据列表
     */
    public PageResult(Long total, Integer pageNum, Integer pageSize, List<T> list) {
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = calculatePages(total, pageSize);
        this.list = list;
    }
    
    /**
     * 创建空的分页结果
     * 
     * @param <T> 数据类型
     * @return 空的分页结果
     */
    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L, 1, 10, Collections.emptyList());
    }
    
    /**
     * 创建分页结果
     * 
     * @param total 总记录数
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param list 数据列表
     * @param <T> 数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(Long total, Integer pageNum, Integer pageSize, List<T> list) {
        return new PageResult<>(total, pageNum, pageSize, list);
    }
    
    /**
     * 从MyBatis Plus的IPage创建分页结果
     * 注意：这是一个兼容方法，实际转换在mybatis模块中实现
     * 
     * @param total 总记录数
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param list 数据列表
     * @param <T> 数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> build(Long total, Integer pageNum, Integer pageSize, List<T> list) {
        return new PageResult<>(total, pageNum, pageSize, list);
    }
    
    /**
     * 计算总页数
     * 
     * @param total 总记录数
     * @param pageSize 每页大小
     * @return 总页数
     */
    private Integer calculatePages(Long total, Integer pageSize) {
        if (total == null || total == 0 || pageSize == null || pageSize == 0) {
            return 0;
        }
        return (int) ((total + pageSize - 1) / pageSize);
    }
    
    /**
     * 是否有下一页
     * 
     * @return true-有下一页 false-无下一页
     */
    public boolean hasNext() {
        return this.pageNum != null && this.pages != null && this.pageNum < this.pages;
    }
    
    /**
     * 是否有上一页
     * 
     * @return true-有上一页 false-无上一页
     */
    public boolean hasPrevious() {
        return this.pageNum != null && this.pageNum > 1;
    }
}

