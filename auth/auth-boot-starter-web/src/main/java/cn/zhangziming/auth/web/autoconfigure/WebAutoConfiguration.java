package cn.zhangziming.auth.web.autoconfigure;

import cn.zhangziming.auth.web.config.JacksonConfig;
import cn.zhangziming.auth.web.config.WebMvcConfig;
import cn.zhangziming.auth.web.interceptor.RequestLogInterceptor;
import cn.zhangziming.auth.web.interceptor.TraceIdInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web自动配置
 * 
 * <p>自动配置Web增强功能，包括CORS、Jackson、拦截器等
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "auth-boot.web", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import({WebMvcConfig.class, JacksonConfig.class})
public class WebAutoConfiguration implements WebMvcConfigurer {

    /**
     * 注册链路追踪拦截器
     */
    @Bean
    public TraceIdInterceptor traceIdInterceptor() {
        return new TraceIdInterceptor();
    }

    /**
     * 注册请求日志拦截器
     */
    @Bean
    @ConditionalOnProperty(prefix = "auth-boot.web.log", name = "enabled", havingValue = "true", matchIfMissing = true)
    public RequestLogInterceptor requestLogInterceptor() {
        return new RequestLogInterceptor();
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 链路追踪拦截器
        registry.addInterceptor(traceIdInterceptor())
                .addPathPatterns("/**")
                .order(1);
        
        // 请求日志拦截器
        registry.addInterceptor(requestLogInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/error")
                .order(2);
    }
}

