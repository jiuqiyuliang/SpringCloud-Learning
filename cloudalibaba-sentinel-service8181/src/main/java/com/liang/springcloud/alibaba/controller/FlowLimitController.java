package com.liang.springcloud.alibaba.controller;


import com.liang.springcloud.alibaba.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-09 10:27
 *
 * 流控规则代码
 */

@RestController
public class FlowLimitController {

    @Autowired
    private CommonService commonService;

    @GetMapping("/testQPS")
    public String testQPS(){
        return "————testQPS";
    }

    @GetMapping("/testThreads")
    public String testThreads() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(800);//休息0.8s,模拟业务执行时间
        return "-----testThreads";
    }

    /**
     * 关联测试
     * @return
     */
    @GetMapping("/testA")
    public String testA(){
        return "-----testA";
    }

    @GetMapping("/testB")
    public String testB(){
        return "-----testB";
    }



    /**
     * 链路测试
     * @return
     */
    @GetMapping("/testA01")
    public String testA01(){

        commonService.testCommon();
        return "-----testA01";
    }

    @GetMapping("/testB01")
    public String testB01(){
        commonService.testCommon();
        return "-----testB01";
    }
}
