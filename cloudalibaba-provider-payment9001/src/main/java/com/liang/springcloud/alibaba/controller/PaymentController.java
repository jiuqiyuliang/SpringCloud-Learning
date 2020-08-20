package com.liang.springcloud.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-08-18 15:03
 */

@RestController
public class PaymentController {


    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/nacos/{id}")
    public String getPayment(@PathVariable("id")String id){

        return "nacos regitstry,serverPort: " + serverPort + "\t id:" + id;
    }
}
