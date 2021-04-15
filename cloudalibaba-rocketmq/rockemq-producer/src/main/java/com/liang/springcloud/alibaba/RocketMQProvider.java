package com.liang.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-04-14 17:40
 */

@SpringBootApplication
@EnableBinding({Source.class})
public class RocketMQProvider {


    public static void main(String[] args) {
        SpringApplication.run(RocketMQProvider.class, args);
    }
}
