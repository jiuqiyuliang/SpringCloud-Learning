package com.liang.springcloud.alibaba.config;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-18 15:56
 */
@Configuration
public class SentinelConfig {

    @PostConstruct
    public void init(){

        WebCallbackManager.setRequestOriginParser(new IpRequestOriginParser());
    }
}
