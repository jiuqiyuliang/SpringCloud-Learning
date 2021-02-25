package com.liang.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-02-25 09:09
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SentinelGatewayNaco7005 {

    public static void main(String[] args) {
        SpringApplication.run(SentinelGatewayNaco7005.class,args);
    }
}
