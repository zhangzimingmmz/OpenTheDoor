package cn.zhangziming.auth.web.interceptor;

import cn.zhangziming.auth.common.constant.CommonConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * 链路追踪拦截器
 * 
 * <p>为每个请求生成唯一的TraceId，方便日志追踪和问题排查
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
public class TraceIdInterceptor implements HandlerInterceptor {

    /**
     * 请求前置处理
     * 生成TraceId并放入MDC和响应头
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头获取TraceId，如果没有则生成新的
        String traceId = request.getHeader(CommonConstant.TRACE_ID_HEADER);
        if (traceId == null || traceId.trim().isEmpty()) {
            traceId = generateTraceId();
        }
        
        // 放入MDC，供日志使用
        MDC.put(CommonConstant.TRACE_ID_HEADER, traceId);
        
        // 放入响应头，返回给前端
        response.setHeader(CommonConstant.TRACE_ID_HEADER, traceId);
        
        return true;
    }

    /**
     * 请求完成后清理
     * 避免内存泄漏
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception ex) {
        MDC.remove(CommonConstant.TRACE_ID_HEADER);
    }

    /**
     * 生成TraceId
     * 使用UUID去掉横杠
     */
    private String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

