package com.liang.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-22 10:48
 */
@SpringBootApplication
@EnableFeignClients
public class Sentinel7002 {

    public static void main(String[] args) {
        SpringApplication.run(Sentinel7002.class,args);
    }

}
