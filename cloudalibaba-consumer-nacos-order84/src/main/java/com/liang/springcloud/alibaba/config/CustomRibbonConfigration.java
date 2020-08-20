package com.liang.springcloud.alibaba.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION: 替换负载均衡算法
 * @DATE: 2020-08-18 17:04
 */
@Configuration
public class CustomRibbonConfigration {
    /**
     * 自定义Ribbon负载均衡的规则
     * @return
     */
    @Bean
    public IRule ribbonRule(){
        return new RandomRule();
    }
}
