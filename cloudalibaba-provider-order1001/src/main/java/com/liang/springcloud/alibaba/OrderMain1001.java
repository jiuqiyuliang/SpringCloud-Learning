package com.liang.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-21 16:33
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderMain1001 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain1001.class,args);
    }

}