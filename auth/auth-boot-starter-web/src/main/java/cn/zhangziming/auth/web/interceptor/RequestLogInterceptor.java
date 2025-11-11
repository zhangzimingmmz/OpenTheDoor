package cn.zhangziming.auth.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 请求日志拦截器
 * 
 * <p>记录HTTP请求的详细信息，包括URL、方法、参数、响应时间、IP等
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
public class RequestLogInterceptor implements HandlerInterceptor {

    private static final String START_TIME_ATTR = "startTime";

    /**
     * 请求前置处理
     * 记录请求开始时间
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 记录请求开始时间
        request.setAttribute(START_TIME_ATTR, System.currentTimeMillis());
        
        // 记录请求信息
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String ip = getClientIp(request);
        
        String url = queryString != null ? uri + "?" + queryString : uri;
        log.info("请求开始 => {} {} from {}", method, url, ip);
        
        return true;
    }

    /**
     * 请求处理后
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
                          Object handler, ModelAndView modelAndView) {
        // 可在此处添加额外逻辑
    }

    /**
     * 请求完成后
     * 记录响应时间
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                               Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTR);
        if (startTime != null) {
            long executeTime = System.currentTimeMillis() - startTime;
            String method = request.getMethod();
            String uri = request.getRequestURI();
            int status = response.getStatus();
            
            if (ex != null) {
                log.error("请求异常 => {} {} | 状态码: {} | 耗时: {}ms | 异常: {}", 
                         method, uri, status, executeTime, ex.getMessage());
            } else {
                log.info("请求完成 => {} {} | 状态码: {} | 耗时: {}ms", 
                        method, uri, status, executeTime);
            }
        }
    }

    /**
     * 获取客户端真实IP地址
     * 考虑代理和负载均衡的情况
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        // 多个IP时取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        
        return ip;
    }
}

