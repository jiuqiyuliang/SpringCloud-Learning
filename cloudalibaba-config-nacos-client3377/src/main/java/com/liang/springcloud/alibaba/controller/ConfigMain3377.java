package com.liang.springcloud.alibaba.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-08-20 17:20
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigMain3377 {

    public static void main(String[] args) {
        SpringApplication.run(ConfigMain3377.class,args);
    }
}
