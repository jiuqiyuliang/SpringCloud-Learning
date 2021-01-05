package com.liang.springcloud.alibaba;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-01-05 10:55
 */

@RestController
public class Controller {

    @GetMapping("/testQPS")
    public String testQPS(){
        return "————testQPS";
    }

    @GetMapping("/testRT")
    public String testRT() throws InterruptedException {

        TimeUnit.MILLISECONDS.sleep(300);
        return "——————testRT";
    }
}
