package com.liang.springcloud.alibaba.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-21 16:46
 */

@RestController
public class UserController {


    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-order-service}")
    private String serverURL;


    @GetMapping("/consumer/resttemplate/{id}")
    @ResponseBody
    public String getOrderInfo(@PathVariable("id")Long id) throws Exception{

        String result = restTemplate.getForObject(serverURL + "/order/" + id, String.class);

        TimeUnit.MILLISECONDS.sleep(300);

        return result;
    }
}
