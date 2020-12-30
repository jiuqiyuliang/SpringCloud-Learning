package com.liang.springcloud.alibaba.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.liang.springcloud.alibaba.handler.SentinelRestTemplateUtil;
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
    @LoadBalanced //负载均衡
    @SentinelRestTemplate
//    @SentinelRestTemplate(fallback = "fallback", fallbackClass = SentinelRestTemplateUtil.FallbackUtil.class, urlCleaner = "urlCleaner", urlCleanerClass = SentinelRestTemplateUtil.UrlCleanerUtil.class)
//    @SentinelRestTemplate(fallback = "fallback", fallbackClass = SentinelRestTemplateUtil.FallbackUtil.class, blockHandler = "blockHandler", blockHandlerClass = SentinelRestTemplateUtil.BlockHandlerUtil.class, urlCleaner = "urlCleaner", urlCleanerClass = SentinelRestTemplateUtil.UrlCleanerUtil.class)
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
