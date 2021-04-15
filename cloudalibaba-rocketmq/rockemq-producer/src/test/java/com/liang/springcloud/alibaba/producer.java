package com.liang.springcloud.alibaba;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-04-14 10:28
 */

@SpringBootTest
public class producer {

    @Resource
    RocketMQTemplate rocketMQTemplate;

    @Test
    void simpleMQ(){


    }
}
