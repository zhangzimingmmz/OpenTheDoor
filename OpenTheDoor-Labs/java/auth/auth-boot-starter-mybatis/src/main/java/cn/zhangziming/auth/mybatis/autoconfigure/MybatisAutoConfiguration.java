package cn.zhangziming.auth.mybatis.autoconfigure;

import cn.zhangziming.auth.mybatis.config.MybatisPlusConfig;
import cn.zhangziming.auth.mybatis.handler.CustomMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

/**
 * MyBatis 自动配置
 * 
 * <p>自动配置MyBatis Plus相关功能
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@AutoConfiguration
@ConditionalOnClass(name = "com.baomidou.mybatisplus.core.MybatisConfiguration")
@ConditionalOnProperty(prefix = "auth-boot.mybatis", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import({MybatisPlusConfig.class, CustomMetaObjectHandler.class})
@MapperScan(basePackages = {"cn.zhangziming.auth.**.mapper"})
public class MybatisAutoConfiguration {
    
    // 配置类，主要通过@Import引入其他配置
}

