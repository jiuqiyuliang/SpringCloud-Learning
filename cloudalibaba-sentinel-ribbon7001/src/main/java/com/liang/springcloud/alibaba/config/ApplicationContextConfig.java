package com.liang.springcloud.alibaba.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-21 16:46
 */
@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced //整合Ribbon 的注解
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
