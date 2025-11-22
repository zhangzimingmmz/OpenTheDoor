package cn.zhangziming.auth.mybatis.util;

import cn.zhangziming.auth.common.model.PageRequest;
import cn.zhangziming.auth.common.model.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 分页工具类
 * 
 * <p>提供PageRequest到MyBatis Plus Page的转换，以及IPage到PageResult的转换
 *
 * @author zhangziming
 * @since 2024-10-29
 */
public class PageUtil {

    /**
     * 将PageRequest转换为MyBatis Plus的Page对象
     *
     * @param pageRequest 分页请求参数
     * @param <T>         实体类型
     * @return MyBatis Plus的Page对象
     */
    public static <T> Page<T> toPage(PageRequest pageRequest) {
        if (pageRequest == null) {
            return new Page<>(1, 10);
        }
        return new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
    }

    /**
     * 将MyBatis Plus的IPage转换为PageResult
     *
     * @param page MyBatis Plus的分页对象
     * @param <T>  实体类型
     * @return 统一分页结果
     */
    public static <T> PageResult<T> toPageResult(IPage<T> page) {
        if (page == null) {
            return new PageResult<>();
        }
        return PageResult.build(
                page.getTotal(),
                (int) page.getCurrent(),
                (int) page.getSize(),
                page.getRecords()
        );
    }

    /**
     * 构建分页对象（带排序）
     *
     * @param pageRequest 分页请求参数
     * @param <T>         实体类型
     * @return MyBatis Plus的Page对象
     */
    public static <T> Page<T> buildPage(PageRequest pageRequest) {
        Page<T> page = toPage(pageRequest);
        
        // 设置排序
        if (pageRequest != null && pageRequest.getOrderBy() != null && !pageRequest.getOrderBy().isEmpty()) {
            String orderBy = pageRequest.getOrderBy();
            if ("asc".equalsIgnoreCase(pageRequest.getSortOrder())) {
                page.addOrder(com.baomidou.mybatisplus.core.metadata.OrderItem.asc(orderBy));
            } else {
                page.addOrder(com.baomidou.mybatisplus.core.metadata.OrderItem.desc(orderBy));
            }
        }
        
        return page;
    }
}

