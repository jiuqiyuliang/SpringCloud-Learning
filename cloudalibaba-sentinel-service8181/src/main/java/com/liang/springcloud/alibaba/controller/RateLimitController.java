package com.liang.springcloud.alibaba.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-10 14:37
 *
 * 降级规则代码
 */

@RestController
public class RateLimitController {

    @GetMapping("/testRT")
    public String testRT() throws InterruptedException {

        TimeUnit.MILLISECONDS.sleep(300);
        return "——————testRT";
    }

    @GetMapping("/testException")
    public String testException() {

        int i = 10/0;

        return "——————testException";
    }

}
