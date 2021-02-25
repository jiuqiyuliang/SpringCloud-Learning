package com.liang.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-02-24 15:18
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SentinelGateway7004 {

    public static void main(String[] args) {
        SpringApplication.run(SentinelGateway7004.class,args);
    }


}
