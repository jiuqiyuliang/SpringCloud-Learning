package com.liang.springcloud.alibaba.controller;

import com.liang.springcloud.alibaba.service.PaymentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-08-19 17:26
 */

@RestController
public class OrderNacosController {

    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/openfeign/{id}")
    public String fallback(@PathVariable("id")String id){


        String result = paymentService.getPayment(id);

        System.out.println(result);
        return result;
    }
}
