package com.liang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-06-29 16:56
 */
@RestController
public class TestController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/test")
    public String test() {
        // 通过spring cloud common中的负载均衡接口选取服务提供节点实现接口调用
//        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-discovery-provider");
//        String url = serviceInstance.getUri() + "/hello/" + "xiaoliang";
//        RestTemplate restTemplate = new RestTemplate();
//
//        String result = restTemplate.getForObject(url, String.class);

        String result = restTemplate.getForObject("http://nacos-discovery-provider" + "/hello/" + "xiaoliang", String.class);

        return result;
    }
}
