package cn.zhangziming.auth.server.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * DDL Runner移除处理器
 * 
 * <p>在Bean创建之前移除有问题的ddlApplicationRunner bean定义
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Component
public class DdlRunnerRemovalProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 检查并移除ddlApplicationRunner bean定义
        if (beanFactory.containsBeanDefinition("ddlApplicationRunner")) {
            System.out.println("✅ 移除有问题的ddlApplicationRunner bean定义");
            ((org.springframework.beans.factory.support.BeanDefinitionRegistry) beanFactory)
                    .removeBeanDefinition("ddlApplicationRunner");
        }
    }
}


