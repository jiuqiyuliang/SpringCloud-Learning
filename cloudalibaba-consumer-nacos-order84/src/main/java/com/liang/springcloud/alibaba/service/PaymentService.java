package com.liang.springcloud.alibaba.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-08-19 17:27
 */

@FeignClient(value = "nacos-payment-provider") //声明服务提供者的name
@Component
public interface PaymentService {

    @GetMapping("/payment/{id}")
    public String getPayment(@PathVariable("id")String id);
}
