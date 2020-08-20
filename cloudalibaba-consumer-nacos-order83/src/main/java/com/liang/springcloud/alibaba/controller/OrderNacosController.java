package com.liang.springcloud.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-08-18 15:08
 */
@RestController
public class OrderNacosController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-payment-service}")
    private String serverURL;

    @GetMapping("/consumer/payment/nacos/{id}")
    public String getPaymentInfo(@PathVariable("id")String id){

        String result = restTemplate.getForObject(serverURL + "/payment/nacos/" + id, String.class);

        System.out.println(result);

        return result;
    }
}
