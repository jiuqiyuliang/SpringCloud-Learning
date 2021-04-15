package com.liang.springcloud.alibaba.controller;

import com.liang.springcloud.alibaba.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-04-14 17:46
 */

@RestController
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @RequestMapping("/hello")
    public void sendMessage(@RequestParam(value = "name", defaultValue = "World") String name){

        providerService.send("Hello " + name);

        System.out.println("发送完成");
    }

}
